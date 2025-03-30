package com.dkprint.wayfarer.task.request.global.infrastructure

import com.dkprint.wayfarer.task.request.global.exception.S3ClientException
import com.dkprint.wayfarer.task.request.global.exception.S3ServerException
import java.time.Duration
import java.util.concurrent.CompletableFuture
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.Delete
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsRequest
import software.amazon.awssdk.services.s3.model.ObjectIdentifier
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest

@Service
class S3Service(
    private val s3AsyncClient: S3AsyncClient,
    private val s3Presigner: S3Presigner,

    @Value("\${s3.bucketName}")
    private val bucketName: String,
) {
    fun upload(
        id: Long,
        productName: String,
        file: MultipartFile,
    ): CompletableFuture<String> {
        val directoryPath = "$id/$productName/${file.originalFilename}"
        val putObjectRequest: PutObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(directoryPath)
            .contentType(file.contentType)
            .build()

        val bytes: ByteArray = file.bytes
        val asyncRequestBody: AsyncRequestBody = AsyncRequestBody.fromBytes(bytes)

        val future: CompletableFuture<String> = CompletableFuture<String>()

        s3AsyncClient.putObject(putObjectRequest, asyncRequestBody)
            .thenAccept {
                future.complete(directoryPath)
            }.exceptionally {
                when (it) {
                    is S3Exception -> future.completeExceptionally(S3ServerException("S3 Server Exception: ${it.message}"))
                    else -> future.completeExceptionally(S3ClientException("S3 Client Exception: ${it.message}"))
                }
                null
            }

        return future
    }

    fun generatePresignedUrl(directoryPathFuture: CompletableFuture<String>): CompletableFuture<String> {
        return directoryPathFuture.thenApply {
            val getObjectRequest: GetObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(it)
                .build()

            val presignedGetObjectRequest: PresignedGetObjectRequest = s3Presigner.presignGetObject { request ->
                request.getObjectRequest(getObjectRequest)
                request.signatureDuration(Duration.ofDays(7))
            }

            presignedGetObjectRequest.url().toString()
        }
    }

    fun delete(id: Long): CompletableFuture<Void> {
        val prefix: String = "$id/"
        val future: CompletableFuture<Void> = CompletableFuture<Void>()
        val listObjectRequest: ListObjectsRequest = ListObjectsRequest.builder()
            .bucket(bucketName)
            .prefix(prefix)
            .build()

        s3AsyncClient.listObjects(listObjectRequest)
            .thenAccept { listedObjects ->
                val identifiers: List<ObjectIdentifier> = listedObjects.contents().map {
                    ObjectIdentifier.builder()
                        .key(it.key())
                        .build()
                }

                val delete: Delete = Delete.builder()
                    .objects(identifiers)
                    .build()

                val deleteObjectRequest: DeleteObjectsRequest = DeleteObjectsRequest.builder()
                    .bucket(bucketName)
                    .delete(delete)
                    .build()

                s3AsyncClient.deleteObjects(deleteObjectRequest)
                    .thenAccept {
                        future.complete(null)
                    }.exceptionally {
                        future.completeExceptionally(RuntimeException("S3 삭제 오류: ${it.message}"))
                        null
                    }
            }.exceptionally {
                future.completeExceptionally(RuntimeException("S3 리스트 오류: ${it.message}"))
                null
            }

        return future
    }
}
