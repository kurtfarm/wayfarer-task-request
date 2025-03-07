package com.dkprint.wayfarer.task.request.domains.printing.application

import com.dkprint.wayfarer.task.request.domains.printing.dao.PrintingRepository
import com.dkprint.wayfarer.task.request.domains.printing.domain.Printing
import com.dkprint.wayfarer.task.request.domains.printing.dto.PrintingDto
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class PrintingService(
    private val printingRepository: PrintingRepository,
    // private val copperplateSdk: CopperplateSdk,
) {
    fun create(taskRequestId: Long, printingDto: PrintingDto) {
        val copperplateId: Long = 1L // copperplateSdk.findIdByCopperplateName(printingDto.copperplateName)
        val printing: Printing = Printing.of(taskRequestId, copperplateId, printingDto)
        printingRepository.save(printing)
    }

    fun delete(taskRequestId: Long) {
        printingRepository.deleteById(taskRequestId)
    }

    fun find(taskRequestId: Long): Printing {
        return printingRepository.findById(taskRequestId).getOrNull()
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }
}
