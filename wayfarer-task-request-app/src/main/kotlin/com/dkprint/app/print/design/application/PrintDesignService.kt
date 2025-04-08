package com.dkprint.app.print.design.application

import com.dkprint.app.core.infrastructure.S3Service
import com.dkprint.app.print.design.dao.PrintDesignRepository
import com.dkprint.app.print.design.domain.PrintDesign
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
        taskRequestNumber: String,
        productName: String,
        file: MultipartFile,
    ): CompletableFuture<Void> {
        val path: CompletableFuture<String> = s3Service.upload(taskRequestNumber, file)
        return path.thenCompose {
            s3Service.generatePresignedUrl(CompletableFuture.completedFuture(it))
        }.thenAccept { url ->
            val printDesign = PrintDesign(taskRequestId = taskRequestId, printDesign = url)
            printDesignRepository.save(printDesign)
        }.exceptionally {
            throw RuntimeException("S3 저장 오류: ${it.message}")
        }
    }

    fun delete(taskRequestId: Long, taskRequestNumber: String) {
        printDesignRepository.deleteByTaskRequestId(taskRequestId)
        s3Service.delete(taskRequestNumber)
    }

    fun find(taskRequestId: Long, taskRequestNumber: String): List<String> {
        val prefix = "$taskRequestNumber/"
        val printDesigns: List<PrintDesign> = printDesignRepository.findByTaskRequestId(taskRequestId)
        val urls: List<String> = s3Service.generatePresignedUrl(prefix).join()
        return printDesigns.mapIndexed { index, printDesign ->
            val url = urls[index]
            printDesign.printDesign = url
            url
        }
    }
}
