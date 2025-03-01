package com.dkprint.wayfarer.task.request.domains.task.request.application

import com.dkprint.wayfarer.task.request.domains.details.application.DetailsService
import com.dkprint.wayfarer.task.request.domains.etc.application.EtcService
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.application.FabricMappingService
import com.dkprint.wayfarer.task.request.domains.lamination.application.LaminationService
import com.dkprint.wayfarer.task.request.domains.print.design.application.PrintDesignService
import com.dkprint.wayfarer.task.request.domains.printing.application.PrintingService
import com.dkprint.wayfarer.task.request.domains.processing.application.ProcessingService
import com.dkprint.wayfarer.task.request.domains.slitting.application.SlittingService
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskRequestFacade(
    private val detailsService: DetailsService,
    private val fabricMappingService: FabricMappingService,
    private val printingService: PrintingService,
    private val laminationService: LaminationService,
    private val slittingService: SlittingService,
    private val etcService: EtcService,
    private val processingService: ProcessingService,
    private val printDesignService: PrintDesignService,
) {
    @Transactional
    fun saveData(
        taskRequestDto: TaskRequestDto,
        taskRequestId: Long,
    ) {
        val productName: String = taskRequestDto.detailsDto.productName

        detailsService.create(taskRequestId, taskRequestDto.detailsDto)
        taskRequestDto.fabricDtos
            .forEach { fabricDto ->
                fabricMappingService.create(taskRequestId, fabricDto)
            }

        with(taskRequestDto) {
            printingDto?.let { printingService.create(taskRequestId, it) }
            laminationDtos?.let {
                it.forEach { laminationDto ->
                    laminationService.create(taskRequestId, laminationDto)
                }
            }
            slittingDto?.let { slittingService.create(taskRequestId, it) }
            listOfNotNull(etc1Dto, etc2Dto).forEach { etcService.create(taskRequestId, it) }
            processingDto?.let { processingService.createProcessing(taskRequestId, it) }
            printDesigns?.let {
                it.forEach { printDesign ->
                    printDesignService.create(taskRequestId, productName, printDesign)
                }
            }
        }
    }
}
