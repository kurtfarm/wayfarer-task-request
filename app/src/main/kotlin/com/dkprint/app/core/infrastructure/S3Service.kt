package com.dkprint.app.core.infrastructure

import com.dkprint.app.core.exception.S3ClientException
import com.dkprint.app.core.exception.S3ServerException
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
    companion object {
        private const val PRESIGNED_URL_DAYS: Long = 1L
    }

    fun upload(
        taskRequestNumber: String,
        file: MultipartFile,
    ): CompletableFuture<String> {
        val path = "$taskRequestNumber/${file.originalFilename}"
        val putObjectRequest: PutObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(path)
            .contentType(file.contentType)
            .build()

        val bytes: ByteArray = file.bytes
        val asyncRequestBody: AsyncRequestBody = AsyncRequestBody.fromBytes(bytes)

        val future: CompletableFuture<String> = CompletableFuture<String>()
        s3AsyncClient.putObject(putObjectRequest, asyncRequestBody)
            .thenAccept {
                future.complete(path)
            }.exceptionally {
                when (it) {
                    is S3Exception -> future.completeExceptionally(S3ServerException("S3 Server Exception: ${it.message}"))
                    else -> future.completeExceptionally(S3ClientException("S3 Client Exception: ${it.message}"))
                }
                null
            }

        return future
    }

    fun generatePresignedUrl(path: CompletableFuture<String>): CompletableFuture<String> {
        return path.thenApply {
            val getObjectRequest: GetObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(it)
                .responseContentType("image/png")
                .build()

            val presignedGetObjectRequest: PresignedGetObjectRequest = s3Presigner.presignGetObject { builder ->
                builder.getObjectRequest(getObjectRequest)
                builder.signatureDuration(Duration.ofDays(PRESIGNED_URL_DAYS))
            }

            presignedGetObjectRequest.url().toString()
        }
    }

    fun generatePresignedUrl(prefix: String): CompletableFuture<List<String>> {
        val listObjectRequest: ListObjectsRequest = ListObjectsRequest.builder()
            .bucket(bucketName)
            .prefix(prefix)
            .build()

        return s3AsyncClient.listObjects(listObjectRequest).thenApply { listedObjects ->
            listedObjects.contents()
                .map { it.key() }
                .map { key ->
                    val getObjectRequest: GetObjectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .responseContentType("image/png")
                        .build()

                    val presignedGetObjectRequest: PresignedGetObjectRequest = s3Presigner.presignGetObject { builder ->
                        builder.getObjectRequest(getObjectRequest)
                        builder.signatureDuration(Duration.ofDays(PRESIGNED_URL_DAYS))
                    }

                    presignedGetObjectRequest.url().toString()
                }
        }
    }


    fun delete(taskRequestNumber: String): CompletableFuture<Void> {
        val prefix: String = "$taskRequestNumber/"
        val future: CompletableFuture<Void> = CompletableFuture<Void>()
        val listObjectRequest: ListObjectsRequest = ListObjectsRequest.builder()
            .bucket(bucketName)
            .prefix(prefix)
            .build()

        s3AsyncClient.listObjects(listObjectRequest)
            .thenAccept { listedObjects ->
                val identifiers: List<ObjectIdentifier> = listedObjects.contents()
                    .map { listedObject ->
                        ObjectIdentifier.builder()
                            .key(listedObject.key())
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
