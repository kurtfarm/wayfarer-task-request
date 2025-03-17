package com.dkprint.wayfarer.task.request.domain.task.request.application

import com.dkprint.wayfarer.task.request.domain.details.application.DetailsService
import com.dkprint.wayfarer.task.request.domain.details.domain.Details
import com.dkprint.wayfarer.task.request.domain.details.dto.DetailsDto
import com.dkprint.wayfarer.task.request.domain.etc.application.EtcService
import com.dkprint.wayfarer.task.request.domain.etc.domain.Etc
import com.dkprint.wayfarer.task.request.domain.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.application.FabricMappingService
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.domain.FabricMapping
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.dto.FabricDto
import com.dkprint.wayfarer.task.request.domain.lamination.application.LaminationService
import com.dkprint.wayfarer.task.request.domain.lamination.domain.Lamination
import com.dkprint.wayfarer.task.request.domain.lamination.dto.LaminationDto
import com.dkprint.wayfarer.task.request.domain.print.design.application.PrintDesignService
import com.dkprint.wayfarer.task.request.domain.printing.application.PrintingService
import com.dkprint.wayfarer.task.request.domain.printing.domain.Printing
import com.dkprint.wayfarer.task.request.domain.printing.dto.PrintingDto
import com.dkprint.wayfarer.task.request.domain.processing.application.ProcessingService
import com.dkprint.wayfarer.task.request.domain.processing.domain.Processing
import com.dkprint.wayfarer.task.request.domain.processing.dto.ProcessingDto
import com.dkprint.wayfarer.task.request.domain.slitting.application.SlittingService
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestReadResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSaveRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSaveResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSearchRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSearchResponse
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
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
        val taskRequest: TaskRequest = taskRequestService.create(taskRequestSaveRequest)
        saveData(taskRequestSaveRequest, taskRequest.id)
        return TaskRequestSaveResponse(
            id = taskRequest.id,
            taskRequestNumber = taskRequest.taskRequestNumber,
            status = true,
        )
    }

    @Transactional
    fun update(taskRequestNumber: String, taskRequestSaveRequest: TaskRequestSaveRequest): TaskRequestSaveResponse {
        val taskRequest: TaskRequest = taskRequestService.update(taskRequestNumber, taskRequestSaveRequest)
        updateData(taskRequestSaveRequest, taskRequest.id)
        return TaskRequestSaveResponse(
            id = taskRequest.id,
            taskRequestNumber = taskRequest.taskRequestNumber,
            status = true,
        )
    }

    @Transactional
    fun delete(taskRequestNumber: String) {
        val taskRequestId: Long = taskRequestService.delete(taskRequestNumber)
        deleteData(taskRequestId)
    }

    @Transactional
    fun search(
        taskRequestSearchRequest: TaskRequestSearchRequest,
        pageable: Pageable,
    ): Page<TaskRequestSearchResponse> {
        val taskRequests: Page<TaskRequest> = taskRequestService.search(taskRequestSearchRequest, pageable)

        return taskRequests.map {
            val taskRequestId: Long = it.id
            val details: Details = detailsService.find(taskRequestId)
            val productStandard: String =
                "${details.standardLength} * ${details.standardWidth} * ${details.standardHeight}"
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

            TaskRequestSearchResponse(
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

    @Transactional
    fun read(taskRequestNumber: String): TaskRequestReadResponse {
        val taskRequest: TaskRequest = taskRequestService.read(taskRequestNumber)
        val taskRequestId: Long = taskRequest.id

        val details: Details = detailsService.find(taskRequestId)
        val fabricMappings: List<FabricMapping> = fabricMappingService.find(taskRequestId)
        val fabricDtos: List<FabricDto> = listOf(
            FabricDto(
                fabricClass = 1,
                fabricType = "양면PET",
                standardThickness = 12,
                standardWidth = 490,
                standardLength = 6000,
                quantity = 1,
                vendorName = "두성",
                expectedArrivalDate = LocalDate.of(2025, 5, 30),
            ),
            FabricDto(
                fabricClass = 2,
                fabricType = "AL",
                standardThickness = 6,
                standardWidth = 490,
                standardLength = 6000,
                quantity = 1,
                vendorName = "두성",
                expectedArrivalDate = LocalDate.of(2025, 5, 30),
            )
        ) // fabricMappings.map { fabricSdk.findById(it.fabricId) }
        val printing: Printing = printingService.find(taskRequestId)
        val laminations: List<Lamination> = laminationService.find(taskRequestId)
        val etc1: Etc = etcService.findEtc1(taskRequestId)
        val etc2: Etc = etcService.findEtc2(taskRequestId)
        val processing: Processing = processingService.find(taskRequestId)
        val printDesigns: List<String> = printDesignService.find(taskRequestId)

        val detailsVendorName: String = "대경" // vendorSdk.findByVendorId(details.vendorId)
        val copperplateName: String = "동판" //
        val etc1VendorName: String = "대경" // vendorSdk.findByVendorId(etc1.vendorId)
        val etc2VendorName: String = "대경" // vendorSdk.findByVendorId(etc2.vendorId)
        val processingVendorName: String = "대경" // vendorSdk.findByVendorId(processing.vendorId)

        return TaskRequestReadResponse(
            detailsDto = DetailsDto.of(details, detailsVendorName),
            fabricDtos = fabricDtos,
            printingDto = PrintingDto.of(printing, copperplateName),
            laminationDtos = laminations.map {
                val taskVendorName: String = "대경" // vendorSdk.findByVendorId(it.taskRequestId)
                LaminationDto.of(it, taskVendorName)
            },
            etc1Dto = EtcDto.of(etc1, etc1VendorName),
            etc2Dto = EtcDto.of(etc2, etc2VendorName),
            processingDto = ProcessingDto.of(processing, processingVendorName),
            printingDesigns = printDesigns,
        )
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
