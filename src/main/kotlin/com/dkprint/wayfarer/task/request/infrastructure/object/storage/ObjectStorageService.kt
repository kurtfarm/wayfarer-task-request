package com.dkprint.wayfarer.task.request.infrastructure.`object`.storage

import org.springframework.web.multipart.MultipartFile

interface ObjectStorageService {
    fun upload(id: Long, productName: String, file: MultipartFile): String
    fun generatePreSignedUrl(directoryPath: String): String
    fun delete(id: Long)
}
