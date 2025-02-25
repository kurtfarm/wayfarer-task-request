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
import com.dkprint.wayfarer.task.request.domains.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,

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
        val savedTaskRequest: TaskRequest = createTaskRequest(taskRequestDto)
        val taskRequestId: Long = savedTaskRequest.id
        val productName: String = taskRequestDto.detailsDto.productName

        detailsService.create(taskRequestId, taskRequestDto.detailsDto)
        fabricMappingService.create(taskRequestId, taskRequestDto.fabricDtos)

        with(taskRequestDto) {
            printingDto?.let { printingService.create(taskRequestId, it) }
            laminationDtos?.let { laminationService.create(taskRequestId, it) }
            slittingDto?.let { slittingService.create(taskRequestId, it) }
            listOfNotNull(etc1Dto, etc2Dto).forEach { etcService.create(taskRequestId, it) }
            processingDto?.let { processingService.createProcessing(taskRequestId, it) }
            printDesigns?.let { printDesignService.create(taskRequestId, productName, it) }
        }

        return TaskRequestResponse(id = taskRequestId, status = true)
    }

    private fun createTaskRequest(
        taskRequestDto: TaskRequestDto,
    ): TaskRequest {
        val taskRequest: TaskRequest = TaskRequest.from(taskRequestDto)
        return taskRequestRepository.save(taskRequest)
    }
}
