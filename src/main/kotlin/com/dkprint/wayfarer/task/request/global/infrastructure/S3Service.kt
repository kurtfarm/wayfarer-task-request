package com.dkprint.wayfarer.task.request.global.infrastructure

import com.dkprint.wayfarer.task.request.global.exception.S3ClientException
import com.dkprint.wayfarer.task.request.global.exception.S3ServerException
import java.time.Duration
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.Delete
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsRequest
import software.amazon.awssdk.services.s3.model.ListObjectsResponse
import software.amazon.awssdk.services.s3.model.ObjectIdentifier
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest

@Service
class S3Service(
    private val s3Client: S3Client,
    private val s3Presigner: S3Presigner,

    @Value("\${s3.bucketName}")
    private val bucketName: String,
) {
    fun upload(
        id: Long,
        productName: String,
        file: MultipartFile,
    ): String {
        val directoryPath = "$id/$productName/${file.originalFilename}"

        try {
            val putObjectRequest: PutObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(directoryPath)
                .contentType(file.contentType)
                .build()

            val requestBody: RequestBody = RequestBody.fromInputStream(file.inputStream, file.size)

            s3Client.putObject(putObjectRequest, requestBody)
        } catch (e: S3Exception) {
            throw S3ServerException("S3 Server Exception: ${e.message}")
        } catch (e: Exception) {
            throw S3ClientException("S3 Client Exception: ${e.message}")
        }

        return directoryPath
    }

    fun generatePresignedUrl(
        directoryPath: String,
    ): String {
        val getObjectRequest: GetObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(directoryPath)
            .build()

        val presignedGetObjectRequest: PresignedGetObjectRequest = s3Presigner.presignGetObject { request ->
            request.getObjectRequest(getObjectRequest)
            request.signatureDuration(Duration.ofDays(7))
        }

        return presignedGetObjectRequest.url().toString()
    }

    fun delete(id: Long) {
        val prefix: String = "$id/"
        try {
            val listObjectRequest: ListObjectsRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .build()
            val listedObjects: ListObjectsResponse = s3Client.listObjects(listObjectRequest)
            val identifiers: List<ObjectIdentifier> = listedObjects.contents().map {
                ObjectIdentifier.builder()
                    .key(it.key())
                    .build()
            }

            val delete: Delete = Delete.builder().objects(identifiers).build()
            val deleteObjectRequest: DeleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(delete)
                .build()
            s3Client.deleteObjects(deleteObjectRequest)
        } catch (e: Exception) {
            throw RuntimeException("S3 삭제 오류: ${e.message}")
        }
    }
}
