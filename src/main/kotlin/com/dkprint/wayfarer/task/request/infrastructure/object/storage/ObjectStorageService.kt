package com.dkprint.wayfarer.task.request.infrastructure.`object`.storage

import org.springframework.web.multipart.MultipartFile

interface ObjectStorageService {
    fun upload(id: Long, productName: String, printDesigns: List<MultipartFile>)
    fun generatePreSignedUrl(id: Long, productName: String, printDesigns: List<MultipartFile>): List<String>
}
