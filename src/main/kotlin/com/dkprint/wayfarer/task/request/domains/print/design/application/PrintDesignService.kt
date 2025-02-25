package com.dkprint.wayfarer.task.request.domains.print.design.application

import com.dkprint.wayfarer.task.request.domains.common.application.MinioService
import com.dkprint.wayfarer.task.request.domains.print.design.dao.PrintDesignRepository
import com.dkprint.wayfarer.task.request.domains.print.design.domain.PrintDesign
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PrintDesignService(
    private val printDesignRepository: PrintDesignRepository,
    private val minioService: MinioService,
) {
    @Transactional
    fun create(
        id: Long,
        productName: String,
        printDesigns: List<MultipartFile>,
    ) {
        minioService.checkBucket()
        minioService.upload(id, productName, printDesigns)

        val urls: List<String> = minioService.generatePreSignedUrl(id, productName, printDesigns)

        urls.forEach { url ->
            val printDesign: PrintDesign = PrintDesign(taskRequestId = id, printDesign = url)
            printDesignRepository.save(printDesign)
        }
    }
}
