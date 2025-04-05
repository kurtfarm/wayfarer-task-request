package com.dkprint.wayfarer.task.request.printing.application

import com.dkprint.wayfarer.task.request.copperplate.mapping.application.CopperplateMappingService
import com.dkprint.wayfarer.task.request.copperplate.mapping.domain.CopperplateMapping
import com.dkprint.wayfarer.task.request.printing.dao.PrintingRepository
import com.dkprint.wayfarer.task.request.printing.domain.Printing
import com.dkprint.wayfarer.task.request.printing.dto.PrintingDto
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class PrintingService(
    private val printingRepository: PrintingRepository,
    private val copperplateMappingService: CopperplateMappingService,
    // TODO: private val copperplateSdk: CopperplateSdk,
) {
    fun create(taskRequestId: Long, printingDto: PrintingDto) {
        val copperplateId: Long = 1L // TODO: copperplateSdk.findIdByCopperplateName(printingDto.copperplateName)
        val printing: Printing = Printing.of(taskRequestId, copperplateId, printingDto)
        val copperplateMapping: CopperplateMapping = CopperplateMapping.of(taskRequestId, copperplateId)
        printingRepository.save(printing)
        copperplateMappingService.create(copperplateMapping)
    }

    fun delete(taskRequestId: Long) {
        printingRepository.deleteById(taskRequestId)
    }

    fun find(taskRequestId: Long): Printing {
        return printingRepository.findById(taskRequestId).getOrNull()
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }
}
