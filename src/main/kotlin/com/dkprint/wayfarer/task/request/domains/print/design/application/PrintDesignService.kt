package com.dkprint.wayfarer.task.request.domains.print.design.application

import com.dkprint.wayfarer.task.request.domains.common.application.MinioService
import com.dkprint.wayfarer.task.request.domains.print.design.dao.PrintDesignRepository
import com.dkprint.wayfarer.task.request.domains.print.design.domain.PrintDesign
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PrintDesignService(
    private val printDesignRepository: PrintDesignRepository,
    private val minioService: MinioService,
) {
    fun create(
        taskRequestId: Long,
        productName: String,
        file: MultipartFile,
    ) {
        minioService.checkBucket()
        val directoryPath: String = minioService.upload(taskRequestId, productName, file)
        val preSignedUrl: String = minioService.generatePreSignedUrl(directoryPath)
        val printDesign: PrintDesign = PrintDesign(taskRequestId = taskRequestId, printDesign = preSignedUrl)
        printDesignRepository.save(printDesign)
    }

    fun delete(taskRequestId: Long) {
        printDesignRepository.deleteByTaskRequestId(taskRequestId)
        minioService.delete(taskRequestId)
    }
}
