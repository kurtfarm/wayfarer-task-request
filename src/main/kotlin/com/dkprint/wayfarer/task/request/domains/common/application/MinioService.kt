package com.dkprint.wayfarer.task.request.domains.common.application

import com.dkprint.wayfarer.task.request.global.exception.MinioClientException
import com.dkprint.wayfarer.task.request.global.exception.MinioServerException
import com.dkprint.wayfarer.task.request.infrastructure.`object`.storage.ObjectStorageService
import io.minio.BucketExistsArgs
import io.minio.GetPresignedObjectUrlArgs
import io.minio.ListObjectsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectsArgs
import io.minio.Result
import io.minio.errors.ErrorResponseException
import io.minio.errors.MinioException
import io.minio.http.Method
import io.minio.messages.DeleteError
import io.minio.messages.DeleteObject
import io.minio.messages.Item
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MinioService(
    private val minioClient: MinioClient,

    @Value("\${minio.bucketName}")
    private val bucketName: String,
) : ObjectStorageService {
    private val log: Logger = LoggerFactory.getLogger(MinioService::class.java)

    fun checkBucket() {
        if (!isBucketExists(bucketName)) {
            createBucket(bucketName)
        }
    }

    override fun upload(
        id: Long,
        productName: String,
        file: MultipartFile,
    ): String {
        val directoryPath = "$id/$productName/${file.originalFilename}"
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .`object`(directoryPath)
                    .stream(file.inputStream, file.size, -1)
                    .contentType(file.contentType)
                    .build()
            )
        } catch (e: ErrorResponseException) {
            throw MinioServerException("MinIO Server Exception: ${e.message}")
        } catch (e: MinioException) {
            throw MinioClientException("MinIO Client Exception: ${e.message}")
        }
        return directoryPath
    }

    override fun generatePreSignedUrl(
        directoryPath: String,
    ): String {
        return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .`object`(directoryPath)
                .method(Method.GET)
                .build()
        )
    }

    override fun delete(id: Long) {
        val prefix: String = "$id/"
        try {
            val listedObjects: MutableIterable<Result<Item>> = minioClient.listObjects(
                ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .recursive(true)
                    .build()
            )

            val deleteObjects: MutableList<DeleteObject> = mutableListOf()
            listedObjects.forEach { objectResult ->
                val item: Item = objectResult.get()
                val deletingObject = DeleteObject(item.objectName())
                deleteObjects.add(deletingObject)
            }


            val results: Iterable<Result<DeleteError>> = minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                    .bucket(bucketName)
                    .objects(deleteObjects)
                    .build()
            )

            for (result in results) {
                val error = result.get()
                log.info("오브젝트: ${error.objectName()} 삭제 오류, ${error.message()}")
            }
        } catch (e: Exception) {
            throw RuntimeException("MinIO 삭제 오류: ${e.message}")
        }
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
