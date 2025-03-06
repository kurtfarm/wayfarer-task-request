package com.dkprint.wayfarer.task.request.domains.task.request.application

import TaskRequestReadAllResponse
import com.dkprint.wayfarer.task.request.domains.details.application.DetailsService
import com.dkprint.wayfarer.task.request.domains.details.domain.Details
import com.dkprint.wayfarer.task.request.domains.etc.application.EtcService
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.application.FabricMappingService
import com.dkprint.wayfarer.task.request.domains.lamination.application.LaminationService
import com.dkprint.wayfarer.task.request.domains.lamination.domain.Lamination
import com.dkprint.wayfarer.task.request.domains.print.design.application.PrintDesignService
import com.dkprint.wayfarer.task.request.domains.printing.application.PrintingService
import com.dkprint.wayfarer.task.request.domains.processing.application.ProcessingService
import com.dkprint.wayfarer.task.request.domains.processing.domain.Processing
import com.dkprint.wayfarer.task.request.domains.slitting.application.SlittingService
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestSaveRequest
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestSaveResponse
import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import java.time.LocalDate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
    // private val codeSdk: CodeSdk,
    // private val fabricSdk: FabricSdk,
    // private val copperplateSdk: CopperplateSdk,
    // private val vendorSdk: VendorSdk,
) {
    @Transactional
    fun create(taskRequestSaveRequest: TaskRequestSaveRequest): TaskRequestSaveResponse {
        val taskRequestId: Long = taskRequestService.create(taskRequestSaveRequest)
        saveData(taskRequestSaveRequest, taskRequestId)
        return TaskRequestSaveResponse(id = taskRequestId, status = true)
    }

    @Transactional
    fun update(taskRequestNumber: String, taskRequestSaveRequest: TaskRequestSaveRequest): TaskRequestSaveResponse {
        val taskRequestId: Long = taskRequestService.update(taskRequestNumber, taskRequestSaveRequest)
        updateData(taskRequestSaveRequest, taskRequestId)
        return TaskRequestSaveResponse(id = taskRequestId, status = true)
    }

    @Transactional
    fun delete(taskRequestNumber: String) {
        val taskRequestId: Long = taskRequestService.delete(taskRequestNumber)
        deleteData(taskRequestId)
    }

    @Transactional
    fun readAll(pageable: Pageable): Page<TaskRequestReadAllResponse> {
        val taskRequests: Page<TaskRequest> = taskRequestService.readAll(pageable)
        return taskRequests.map {
            val taskRequestId: Long = it.id
            val details: Details = detailsService.find(taskRequestId)
            val productStandard: String =
                "${details.standardLength} * ${details.standardWidth} * ${details.standardThickness}"
            val laminations: List<Lamination> = laminationService.find(taskRequestId)
            val processing: Processing = processingService.find(taskRequestId)
            val code: String = "ABCDEF" // codeSdk.findById(it.codeId)
            val fabricExpectedArrivalDate: LocalDate = LocalDate.now() // fabricSdk.findArrivalDateById(it.id)
            val copperplateExpectedArrivalDate: LocalDate = LocalDate.now() // copperplateSdk.findArrivalDateById(it.id)
            val productVendorName: String = "대경" // vendorSdk.findVendorNameById(details.vendorId)
            val laminationVendorNames: List<String> = listOf(
                "1차 거래처",
                "2차 거래처",
                "1차 거래처",
                "2차 거래처",
            ).distinct() // laminations.map { lamination ->vendorSdk.findVendorNameById(lamination.vendorId) }

            val processingVendorName: String = "대경" // vendorSdk.findVendorNameById(processing.vendorId)

            TaskRequestReadAllResponse(
                taskRequestId = taskRequestId,
                orderDate = details.orderDate,
                taskRequestNumber = it.taskRequestNumber,
                productCode = code,
                productName = details.productName,
                productStandard = productStandard,
                fabricExpectedArrivalDate = fabricExpectedArrivalDate,
                copperplateExpectedArrivalDate = copperplateExpectedArrivalDate,
                productVendorName = productVendorName,
                laminationVendorNames = laminationVendorNames,
                processingVendorName = processingVendorName,
            )
        }
    }

    private fun saveData(taskRequestSaveRequest: TaskRequestSaveRequest, taskRequestId: Long) {
        val productName: String = taskRequestSaveRequest.detailsDto.productName

        detailsService.create(taskRequestId, taskRequestSaveRequest.detailsDto)
        taskRequestSaveRequest.fabricDtos
            .forEach { fabricDto ->
                fabricMappingService.create(taskRequestId, fabricDto)
            }

        with(taskRequestSaveRequest) {
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

    private fun updateData(taskRequestSaveRequest: TaskRequestSaveRequest, taskRequestId: Long) {
        val productName: String = taskRequestSaveRequest.detailsDto.productName

        detailsService.create(taskRequestId, taskRequestSaveRequest.detailsDto)
        fabricMappingService.delete(taskRequestId)
        taskRequestSaveRequest.fabricDtos
            .forEach { fabricDto ->
                fabricMappingService.create(taskRequestId, fabricDto)
            }

        with(taskRequestSaveRequest) {
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
