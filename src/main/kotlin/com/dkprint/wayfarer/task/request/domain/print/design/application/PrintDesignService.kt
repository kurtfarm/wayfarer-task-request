package com.dkprint.wayfarer.task.request.domain.print.design.application

import com.dkprint.wayfarer.task.request.domain.print.design.dao.PrintDesignRepository
import com.dkprint.wayfarer.task.request.domain.print.design.domain.PrintDesign
import com.dkprint.wayfarer.task.request.common.infrastructure.S3Service
import java.util.concurrent.CompletableFuture
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
    ): CompletableFuture<Void> {
        val directoryPath: CompletableFuture<String> = s3Service.upload(taskRequestId, productName, file)

        return directoryPath.thenCompose {
            s3Service.generatePresignedUrl(CompletableFuture.completedFuture(it))
        }.thenAccept {
            val printDesign = PrintDesign(taskRequestId = taskRequestId, printDesign = it)
            printDesignRepository.save(printDesign)
        }.exceptionally {
            throw RuntimeException("S3 저장 오류: ${it.message}")
        }
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
