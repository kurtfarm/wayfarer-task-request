package com.dkprint.wayfarer.task.request.domains.printing.application

import com.dkprint.wayfarer.task.request.domains.printing.dao.PrintingRepository
import com.dkprint.wayfarer.task.request.domains.printing.domain.Printing
import com.dkprint.wayfarer.task.request.domains.printing.dto.PrintingDto
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
}
