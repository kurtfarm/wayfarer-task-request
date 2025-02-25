package com.dkprint.wayfarer.task.request.domains.printing.application

import com.dkprint.wayfarer.task.request.domains.copperplate.dao.CopperplateRepository
import com.dkprint.wayfarer.task.request.domains.copperplate.domain.Copperplate
import com.dkprint.wayfarer.task.request.domains.printing.dao.PrintingRepository
import com.dkprint.wayfarer.task.request.domains.printing.domain.Printing
import com.dkprint.wayfarer.task.request.domains.printing.dto.PrintingDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PrintingService(
    private val printingRepository: PrintingRepository,
    private val copperplateRepository: CopperplateRepository,
) {
    @Transactional
    fun create(
        taskRequestId: Long,
        printingDto: PrintingDto,
    ) {
        val copperplate: Copperplate = copperplateRepository.findByCopperplateName(printingDto.copperplateName)
            ?: throw IllegalArgumentException("동판: ${printingDto.copperplateName} 조회 오류")
        val printing: Printing = Printing.of(taskRequestId, copperplate.id, printingDto)
        printingRepository.save(printing)
    }
}
