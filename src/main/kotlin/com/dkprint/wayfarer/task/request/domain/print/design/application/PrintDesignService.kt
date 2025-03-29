package com.dkprint.wayfarer.task.request.domain.print.design.application

import com.dkprint.wayfarer.task.request.domain.print.design.dao.PrintDesignRepository
import com.dkprint.wayfarer.task.request.domain.print.design.domain.PrintDesign
import com.dkprint.wayfarer.task.request.global.infrastructure.S3Service
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PrintDesignService(
    private val printDesignRepository: PrintDesignRepository,
    private val s3Service: S3Service,
) {
    fun create(
        taskRequestId: Long,
        productName: String,
        file: MultipartFile,
    ) {
        s3Service.checkBucket()
        val directoryPath: String = s3Service.upload(taskRequestId, productName, file)
        val preSignedUrl: String = s3Service.generatePresignedUrl(directoryPath)
        val printDesign: PrintDesign = PrintDesign(taskRequestId = taskRequestId, printDesign = preSignedUrl)
        printDesignRepository.save(printDesign)
    }

    fun delete(taskRequestId: Long) {
        printDesignRepository.deleteByTaskRequestId(taskRequestId)
        s3Service.delete(taskRequestId)
    }

    fun find(taskRequestId: Long): List<String> {
        return printDesignRepository.findByTaskRequestId(taskRequestId)
            .map { it.printDesign }
    }
}
