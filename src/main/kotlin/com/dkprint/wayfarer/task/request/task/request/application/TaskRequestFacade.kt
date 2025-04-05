package com.dkprint.wayfarer.task.request.task.request.application

import com.dkprint.wayfarer.task.request.copperplate.mapping.application.FabricMappingService
import com.dkprint.wayfarer.task.request.copperplate.mapping.domain.FabricMapping
import com.dkprint.wayfarer.task.request.copperplate.mapping.dto.FabricDto
import com.dkprint.wayfarer.task.request.core.dto.Paging
import com.dkprint.wayfarer.task.request.details.application.DetailsService
import com.dkprint.wayfarer.task.request.details.domain.Details
import com.dkprint.wayfarer.task.request.details.dto.DetailsDto
import com.dkprint.wayfarer.task.request.etc.application.EtcService
import com.dkprint.wayfarer.task.request.etc.domain.Etc
import com.dkprint.wayfarer.task.request.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.lamination.application.LaminationService
import com.dkprint.wayfarer.task.request.lamination.domain.Lamination
import com.dkprint.wayfarer.task.request.lamination.dto.LaminationDto
import com.dkprint.wayfarer.task.request.print.design.application.PrintDesignService
import com.dkprint.wayfarer.task.request.printing.application.PrintingService
import com.dkprint.wayfarer.task.request.printing.domain.Printing
import com.dkprint.wayfarer.task.request.printing.dto.PrintingDto
import com.dkprint.wayfarer.task.request.processing.application.ProcessingService
import com.dkprint.wayfarer.task.request.processing.domain.Processing
import com.dkprint.wayfarer.task.request.processing.dto.ProcessingDto
import com.dkprint.wayfarer.task.request.slitting.application.SlittingService
import com.dkprint.wayfarer.task.request.task.request.api.dto.request.ReadAllRequest
import com.dkprint.wayfarer.task.request.task.request.api.dto.request.SearchRequest
import com.dkprint.wayfarer.task.request.task.request.api.dto.request.UpsertRequest
import com.dkprint.wayfarer.task.request.task.request.api.dto.response.ReadAllResponse
import com.dkprint.wayfarer.task.request.task.request.api.dto.response.ReadResponse
import com.dkprint.wayfarer.task.request.task.request.api.dto.response.SearchResponse
import com.dkprint.wayfarer.task.request.task.request.api.dto.response.UpsertResponse
import com.dkprint.wayfarer.task.request.task.request.domain.TaskRequest
import java.time.LocalDate
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class TaskRequestFacade(
    private val taskRequestService: TaskRequestService,
    private val searchFacade: SearchFacade,
    private val detailsService: DetailsService,
    private val fabricMappingService: FabricMappingService,
    private val printingService: PrintingService,
    private val laminationService: LaminationService,
    private val slittingService: SlittingService,
    private val etcService: EtcService,
    private val processingService: ProcessingService,
    private val printDesignService: PrintDesignService,
    // TODO: private val codeSdk: CodeSdk,
    // TODO: private val fabricSdk: FabricSdk,
    // TODO: private val copperplateSdk: CopperplateSdk,
    // TODO: private val vendorSdk: VendorSdk,
) {
    @Transactional
    fun create(request: UpsertRequest, printDesigns: List<MultipartFile>?): UpsertResponse {
        val taskRequest: TaskRequest = taskRequestService.create(request)
        createCascade(taskRequest.id, taskRequest.taskRequestNumber, request, printDesigns)
        return UpsertResponse(taskRequest.id, taskRequest.taskRequestNumber)
    }

    @Transactional
    fun read(taskRequestNumber: String): ReadResponse {
        val taskRequest: TaskRequest = taskRequestService.read(taskRequestNumber)
        val taskRequestId: Long = taskRequest.id

        val details: Details = detailsService.findByTaskRequestId(taskRequestId)
        val fabricMappings: List<FabricMapping> = fabricMappingService.findByTaskRequestId(taskRequestId)
        val fabricDtos: List<FabricDto> = listOf(
            FabricDto(
                fabricClass = 1,
                fabricType = "양면PET",
                standardThickness = 12,
                standardWidth = 490,
                standardLength = 6000,
                quantity = 1,
                vendorName = "가기업",
                expectedArrivalDate = LocalDate.of(2025, 5, 30),
            ),
            FabricDto(
                fabricClass = 2,
                fabricType = "AL",
                standardThickness = 6,
                standardWidth = 490,
                standardLength = 6000,
                quantity = 1,
                vendorName = "나기업",
                expectedArrivalDate = LocalDate.of(2025, 5, 30),
            )
        ) // TODO: fabricMappings.map { fabricSdk.findById(it.fabricId) }
        val printing: Printing = printingService.find(taskRequestId)
        val laminations: List<Lamination> = laminationService.find(taskRequestId)
        val etc1: Etc = etcService.findEtc1(taskRequestId)
        val etc2: Etc = etcService.findEtc2(taskRequestId)
        val processing: Processing = processingService.find(taskRequestId)
        val printDesigns: List<String> = printDesignService.find(taskRequestId, taskRequestNumber)

        val detailsVendorName: String = "가기업" // TODO: vendorSdk.findByVendorId(details.vendorId)
        val copperplateName: String = "나기업" // TODO: copperplateSdk.findBy
        val etc1VendorName: String = "다기업" // TODO: vendorSdk.findByVendorId(etc1.vendorId)
        val etc2VendorName: String = "라기업" // TODO: vendorSdk.findByVendorId(etc2.vendorId)
        val processingVendorName: String = "마기업" // TODO: vendorSdk.findByVendorId(processing.vendorId)

        return ReadResponse(
            detailsDto = DetailsDto.of(details, detailsVendorName),
            fabricDtos = fabricDtos,
            printingDto = PrintingDto.of(printing, copperplateName),
            laminationDtos = laminations.map { lamination ->
                val taskVendorName: String = "바기업" // TODO: vendorSdk.findByVendorId(lamination.taskRequestId)
                LaminationDto.of(lamination, taskVendorName)
            },
            etc1Dto = EtcDto.of(etc1, etc1VendorName),
            etc2Dto = EtcDto.of(etc2, etc2VendorName),
            processingDto = ProcessingDto.of(processing, processingVendorName),
            printingDesigns = printDesigns,
        )
    }

    @Transactional
    fun readAll(readAllRequest: ReadAllRequest): Paging<ReadAllResponse> {
        val taskRequests: Page<TaskRequest> = taskRequestService.readAll(readAllRequest)
        val pages: Page<ReadAllResponse> = taskRequests.map { taskRequest ->
            val taskRequestId: Long = taskRequest.id
            val details: Details = detailsService.findByTaskRequestId(taskRequestId)
            val productStandard: String =
                "${details.standardLength} * ${details.standardWidth} * ${details.standardHeight}"
            val laminations: List<Lamination> = laminationService.find(taskRequestId)
            val processing: Processing = processingService.find(taskRequestId)
            val code: String = "ABCDEF" // TODO: codeSdk.findById(it.codeId)
            val fabricExpectedArrivalDate: LocalDate = listOf(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
            ).last()  // TODO: fabricSdk.findArrivalDateById(it.id)
            val copperplateExpectedArrivalDate: LocalDate = listOf(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
            ).last() // TODO: copperplateSdk.findArrivalDateById(it.id)
            val productVendorName: String = "대경" // TODO: vendorSdk.findVendorNameById(details.vendorId)
            val laminationVendorNames: List<String> = listOf("1차 거래처", "2차 거래처", "1차 거래처", "2차 거래처")
                .distinct() // TODO: laminations.map { lamination ->vendorSdk.findVendorNameById(lamination.vendorId) }
            val processingVendorName: String = "대경" // TODO: vendorSdk.findVendorNameById(processing.vendorId)

            ReadAllResponse(
                taskRequestId = taskRequestId,
                orderDate = details.orderDate,
                taskRequestNumber = taskRequest.taskRequestNumber,
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
        return Paging.from(pages)
    }

    @Transactional
    fun update(
        taskRequestNumber: String,
        request: UpsertRequest,
        printDesigns: List<MultipartFile>?,
    ): UpsertResponse {
        val taskRequest: TaskRequest = taskRequestService.update(taskRequestNumber, request)
        updateCascade(taskRequest.id, taskRequestNumber, request, printDesigns)
        return UpsertResponse(taskRequest.id, taskRequest.taskRequestNumber)
    }

    @Transactional
    fun delete(taskRequestNumber: String) {
        taskRequestService.delete(taskRequestNumber) // soft delete
        /* hard delete
        val taskRequestId: Long = taskRequestService.delete(taskRequestNumber)
        detailsService.delete(taskRequestId)
        fabricMappingService.delete(taskRequestId)
        printingService.delete(taskRequestId)
        laminationService.delete(taskRequestId)
        slittingService.delete(taskRequestId)
        etcService.delete(taskRequestId)
        processingService.delete(taskRequestId)
        printDesignService.delete(taskRequestId)
        */
    }

    @Transactional
    fun search(request: SearchRequest): Paging<SearchResponse> {
        val taskRequests: Page<TaskRequest> = searchFacade.search(request)
        val pages: Page<SearchResponse> = taskRequests.map { taskRequest ->
            val taskRequestId: Long = taskRequest.id
            val details: Details = detailsService.findByTaskRequestId(taskRequestId)
            val productStandard: String =
                "${details.standardLength} * ${details.standardWidth} * ${details.standardHeight}"
            val laminations: List<Lamination> = laminationService.find(taskRequestId)
            val processing: Processing = processingService.find(taskRequestId)
            val code: String = "ABCDEF" // TODO: codeSdk.findById(it.codeId)
            val fabricExpectedArrivalDate: LocalDate = listOf(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
            ).last()  // TODO: fabricSdk.findArrivalDateById(it.id)
            val copperplateExpectedArrivalDate: LocalDate = listOf(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
            ).last() // TODO: copperplateSdk.findArrivalDateById(it.id)
            val productVendorName: String = "대경" // TODO: vendorSdk.findVendorNameById(details.vendorId)
            val laminationVendorNames: List<String> = listOf("1차 거래처", "2차 거래처", "1차 거래처", "2차 거래처")
                .distinct() // TODO: laminations.map { lamination ->vendorSdk.findVendorNameById(lamination.vendorId) }
            val processingVendorName: String = "대경" // TODO: vendorSdk.findVendorNameById(processing.vendorId)

            SearchResponse(
                taskRequestId = taskRequestId,
                orderDate = details.orderDate,
                taskRequestNumber = taskRequest.taskRequestNumber,
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
        return Paging.from(pages)
    }

    private fun createCascade(
        taskRequestId: Long,
        taskRequestNumber: String,
        request: UpsertRequest,
        printDesigns: List<MultipartFile>?
    ) {
        val productName: String = request.detailsDto.productName

        detailsService.create(taskRequestId, request.detailsDto)
        request.fabricDtos
            .forEach { fabricDto ->
                fabricMappingService.create(taskRequestId, fabricDto)
            }

        with(request) {
            printingDto?.let { printingService.create(taskRequestId, it) }
            laminationDtos?.let {
                it.forEach { laminationDto ->
                    laminationService.create(taskRequestId, laminationDto)
                }
            }
            slittingDto?.let { slittingService.create(taskRequestId, it) }
            listOfNotNull(etc1Dto, etc2Dto).forEach { etcService.create(taskRequestId, it) }
            processingDto?.let { processingService.create(taskRequestId, it) }
        }

        printDesigns?.let {
            it.forEach { printDesign ->
                printDesignService.create(taskRequestId, taskRequestNumber, productName, printDesign)
            }
        }
    }

    private fun updateCascade(
        taskRequestId: Long,
        taskRequestNumber: String,
        request: UpsertRequest,
        printDesigns: List<MultipartFile>?
    ) {
        val productName: String = request.detailsDto.productName

        detailsService.create(taskRequestId, request.detailsDto)
        fabricMappingService.delete(taskRequestId)
        request.fabricDtos
            .forEach { fabricDto ->
                fabricMappingService.create(taskRequestId, fabricDto)
            }

        with(request) {
            printingDto?.let { printingService.create(taskRequestId, it) }
            laminationDtos?.let {
                laminationService.delete(taskRequestId)
                it.forEach { laminationDto ->
                    laminationService.create(taskRequestId, laminationDto)
                }
            }
            slittingDto?.let { slittingService.create(taskRequestId, it) }
            etcService.delete(taskRequestId)
            listOfNotNull(etc1Dto, etc2Dto).forEach {
                etcService.create(taskRequestId, it)
            }
            processingDto?.let { processingService.create(taskRequestId, it) }
            printDesignService.delete(taskRequestId, taskRequestNumber)
        }

        printDesigns?.let {
            it.forEach { printDesign ->
                printDesignService.create(taskRequestId, taskRequestNumber, productName, printDesign)
            }
        }
    }
}
