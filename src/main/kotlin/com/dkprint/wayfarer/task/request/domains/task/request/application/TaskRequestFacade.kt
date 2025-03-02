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
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskRequestFacade(
    private val taskRequestService: TaskRequestService,
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
    fun create(taskRequestDto: TaskRequestDto): TaskRequestResponse {
        val taskRequestId: Long = taskRequestService.create(taskRequestDto)
        saveData(taskRequestDto, taskRequestId)
        return TaskRequestResponse(id = taskRequestId, status = true)
    }

    @Transactional
    fun update(taskRequestNumber: Long, taskRequestDto: TaskRequestDto): TaskRequestResponse {
        val taskRequestId: Long = taskRequestService.update(taskRequestNumber, taskRequestDto)
        updateData(taskRequestDto, taskRequestId)
        return TaskRequestResponse(id = taskRequestId, status = true)
    }

    @Transactional
    fun delete(taskRequestNumber: Long) {
        val taskRequestId: Long = taskRequestService.delete(taskRequestNumber)
        deleteData(taskRequestId)
    }

    private fun saveData(taskRequestDto: TaskRequestDto, taskRequestId: Long) {
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
            processingDto?.let { processingService.create(taskRequestId, it) }
            printDesigns?.let {
                it.forEach { printDesign ->
                    printDesignService.create(taskRequestId, productName, printDesign)
                }
            }
        }
    }

    private fun updateData(taskRequestDto: TaskRequestDto, taskRequestId: Long) {
        val productName: String = taskRequestDto.detailsDto.productName

        detailsService.create(taskRequestId, taskRequestDto.detailsDto)
        fabricMappingService.delete(taskRequestId)
        taskRequestDto.fabricDtos
            .forEach { fabricDto ->
                fabricMappingService.create(taskRequestId, fabricDto)
            }

        with(taskRequestDto) {
            printingDto?.let { printingService.create(taskRequestId, it) }
            laminationService.delete(taskRequestId)
            laminationDtos?.let {
                it.forEach { laminationDto ->
                    laminationService.create(taskRequestId, laminationDto)
                }
            }
            slittingDto?.let { slittingService.create(taskRequestId, it) }
            listOfNotNull(etc1Dto, etc2Dto).forEach { etcService.create(taskRequestId, it) }
            processingDto?.let { processingService.create(taskRequestId, it) }
            printDesignService.delete(taskRequestId)
            printDesigns?.let {
                it.forEach { printDesign ->
                    printDesignService.create(taskRequestId, productName, printDesign)
                }
            }
        }
    }

    private fun deleteData(taskRequestId: Long) {
        detailsService.delete(taskRequestId)
        fabricMappingService.delete(taskRequestId)
        printingService.delete(taskRequestId)
        laminationService.delete(taskRequestId)
        slittingService.delete(taskRequestId)
        etcService.delete(taskRequestId)
        processingService.delete(taskRequestId)
        printDesignService.delete(taskRequestId)
    }
}
