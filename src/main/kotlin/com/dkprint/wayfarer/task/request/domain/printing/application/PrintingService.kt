package com.dkprint.wayfarer.task.request.domain.printing.application

import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.application.CopperplateMappingService
import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.dao.CopperplateMappingRepository
import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.domain.CopperplateMapping
import com.dkprint.wayfarer.task.request.domain.printing.dao.PrintingRepository
import com.dkprint.wayfarer.task.request.domain.printing.domain.Printing
import com.dkprint.wayfarer.task.request.domain.printing.dto.PrintingDto
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class PrintingService(
    private val printingRepository: PrintingRepository,
    private val copperplateMappingService: CopperplateMappingService,
    // private val copperplateSdk: CopperplateSdk,
) {
    fun create(taskRequestId: Long, printingDto: PrintingDto) {
        val copperplateId: Long = 1L // copperplateSdk.findIdByCopperplateName(printingDto.copperplateName)
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
