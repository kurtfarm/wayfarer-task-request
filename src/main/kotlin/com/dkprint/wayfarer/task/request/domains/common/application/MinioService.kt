package com.dkprint.wayfarer.task.request.domains.common.application

import com.dkprint.wayfarer.task.request.global.exception.MinioClientException
import com.dkprint.wayfarer.task.request.global.exception.MinioServerException
import com.dkprint.wayfarer.task.request.infrastructure.`object`.storage.ObjectStorageService
import io.minio.BucketExistsArgs
import io.minio.GetPresignedObjectUrlArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.errors.ErrorResponseException
import io.minio.errors.MinioException
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MinioService(
    private val minioClient: MinioClient,

    @Value("\${minio.bucketName}")
    private val bucketName: String,
) : ObjectStorageService {
    fun checkBucket() {
        if (!isBucketExists(bucketName)) {
            createBucket(bucketName)
        }
    }

    override fun upload(id: Long, productName: String, printDesigns: List<MultipartFile>) {
        printDesigns.forEachIndexed { index, printDesign ->
            val fileName = "$id/$index-$productName/${printDesign.originalFilename}"
            try {
                minioClient.putObject(
                    PutObjectArgs.builder()
                        .bucket(bucketName)
                        .`object`(fileName)
                        .stream(printDesign.inputStream, printDesign.size, -1)
                        .contentType(printDesign.contentType)
                        .build()
                )
            } catch (e: ErrorResponseException) {
                throw MinioServerException("MinIO Server Exception: ${e.message}")
            } catch (e: MinioException) {
                throw MinioClientException("MinIO Client Exception: ${e.message}")
            }
        }
    }

    override fun generatePreSignedUrl(id: Long, productName: String, printDesigns: List<MultipartFile>): List<String> {
        val urls: MutableList<String> = mutableListOf()

        printDesigns.forEachIndexed { index, printDesign ->
            val fileName: String = "$id/$index-$productName/${printDesign.originalFilename}"

            val preSignedUrl: String = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .`object`(fileName)
                    .method(Method.GET)
                    .build()
            )

            urls.add(preSignedUrl)
        }

        return urls
    }

    private fun isBucketExists(bucketName: String): Boolean {
        return minioClient.bucketExists(
            BucketExistsArgs.builder()
                .bucket(bucketName)
                .build()
        )
    }

    private fun createBucket(bucketName: String) {
        minioClient.makeBucket(
            MakeBucketArgs.builder()
                .bucket(bucketName)
                .build()
        )
    }
}
