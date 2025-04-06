package com.dkprint.app.task.request.application

import com.dkprint.app.core.dto.Paging
import com.dkprint.app.core.dto.request.ReadAllRequest
import com.dkprint.app.core.dto.request.SearchRequest
import com.dkprint.app.core.dto.request.UpsertRequest
import com.dkprint.app.core.dto.response.ReadAllResponse
import com.dkprint.app.core.dto.response.ReadResponse
import com.dkprint.app.core.dto.response.SearchResponse
import com.dkprint.app.core.dto.response.UpsertResponse
import com.dkprint.app.details.application.DetailsService
import com.dkprint.app.details.domain.Details
import com.dkprint.app.details.dto.DetailsDto
import com.dkprint.app.etc.application.EtcService
import com.dkprint.app.etc.domain.Etc
import com.dkprint.app.etc.dto.EtcDto
import com.dkprint.app.fabric.mapping.application.FabricMappingService
import com.dkprint.app.fabric.mapping.domain.FabricMapping
import com.dkprint.app.fabric.mapping.dto.FabricDto
import com.dkprint.app.lamination.application.LaminationService
import com.dkprint.app.lamination.domain.Lamination
import com.dkprint.app.lamination.dto.LaminationDto
import com.dkprint.app.print.design.application.PrintDesignService
import com.dkprint.app.printing.application.PrintingService
import com.dkprint.app.printing.domain.Printing
import com.dkprint.app.printing.dto.PrintingDto
import com.dkprint.app.processing.application.ProcessingService
import com.dkprint.app.processing.domain.Processing
import com.dkprint.app.processing.dto.ProcessingDto
import com.dkprint.app.slitting.application.SlittingService
import com.dkprint.app.task.request.domain.TaskRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
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
    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (E)", Locale.KOREAN)
    }

    @Transactional
    fun create(request: UpsertRequest, printDesigns: List<MultipartFile>?): UpsertResponse {
        val taskRequest: TaskRequest = taskRequestService.create(request)
        cascade(taskRequest.id, taskRequest.taskRequestNumber, request, printDesigns, false)
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
        val response: Page<ReadAllResponse> = toResponse(taskRequests)
        return Paging.from(response)
    }

    @Transactional
    fun update(
        taskRequestNumber: String,
        request: UpsertRequest,
        printDesigns: List<MultipartFile>?,
    ): UpsertResponse {
        val taskRequest: TaskRequest = taskRequestService.update(taskRequestNumber, request)
        cascade(taskRequest.id, taskRequestNumber, request, printDesigns, true)
        return UpsertResponse(taskRequest.id, taskRequest.taskRequestNumber)
    }

    @Transactional
    fun search(request: SearchRequest): Paging<SearchResponse> {
        val taskRequests: Page<TaskRequest> = searchFacade.search(request)
        val response: Page<SearchResponse> = toResponse(taskRequests)
        return Paging.from(response)
    }

    private fun cascade(
        taskRequestId: Long,
        taskRequestNumber: String,
        request: UpsertRequest,
        printDesigns: List<MultipartFile>?,
        isUpdate: Boolean,
    ) {
        val productName: String = request.detailsDto.productName

        if (isUpdate) {
            fabricMappingService.delete(taskRequestId)
            laminationService.delete(taskRequestId)
            etcService.delete(taskRequestId)
            printDesignService.delete(taskRequestId, taskRequestNumber)
        }

        with(request) {
            detailsDto.let { detailsService.create(taskRequestId, it) }
            fabricDtos.forEach { fabricMappingService.create(taskRequestId, it) }
            printingDto?.let { printingService.create(taskRequestId, it) }
            laminationDtos?.forEach { laminationService.create(taskRequestId, it) }
            slittingDto?.let { slittingService.create(taskRequestId, it) }
            listOfNotNull(etc1Dto, etc2Dto).forEach { etcService.create(taskRequestId, it) }
            processingDto?.let { processingService.create(taskRequestId, it) }
        }

        printDesigns?.forEach { printDesignService.create(taskRequestId, taskRequestNumber, productName, it) }
    }

    private inline fun <reified T> toResponse(taskRequests: Page<TaskRequest>): Page<T> {
        return taskRequests.map { taskRequest ->
            val taskRequestId: Long = taskRequest.id
            val details: Details = detailsService.findByTaskRequestId(taskRequestId)
            val orderDate: String = details.orderDate.format(formatter)

            val productStandard: String =
                "${details.standardLength} * ${details.standardWidth} * ${details.standardHeight}"
            val laminations: List<Lamination> = laminationService.find(taskRequestId)
            val processing: Processing = processingService.find(taskRequestId)
            val code: String = "ABCDEF" // TODO: codeSdk.findById(it.codeId)

            val fabricExpectedArrivalDate: String = listOf(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
            ).last().format(formatter) // TODO: fabricSdk.findArrivalDateById(it.id)

            val copperplateExpectedArrivalDate: String = listOf(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
            ).last().format(formatter) // TODO: copperplateSdk.findArrivalDateById(it.id)

            val productVendorName: String = "대경" // TODO: vendorSdk.findVendorNameById(details.vendorId)
            val laminationVendorNames: List<String> = listOf("1차 거래처", "2차 거래처", "1차 거래처", "2차 거래처").distinct()
            // TODO: laminations.map { lamination ->vendorSdk.findVendorNameById(lamination.vendorId) }
            val processingVendorName: String = "대경" // TODO: vendorSdk.findVendorNameById(processing.vendorId)

            when (T::class) {
                ReadAllResponse::class -> ReadAllResponse(
                    taskRequestId = taskRequestId,
                    orderDate = orderDate,
                    taskRequestNumber = taskRequest.taskRequestNumber,
                    productCode = code,
                    productName = details.productName,
                    productStandard = productStandard,
                    fabricExpectedArrivalDate = fabricExpectedArrivalDate,
                    copperplateExpectedArrivalDate = copperplateExpectedArrivalDate,
                    productVendorName = productVendorName,
                    laminationVendorNames = laminationVendorNames,
                    processingVendorName = processingVendorName,
                ) as T

                SearchResponse::class -> SearchResponse(
                    taskRequestId = taskRequestId,
                    orderDate = orderDate,
                    taskRequestNumber = taskRequest.taskRequestNumber,
                    productCode = code,
                    productName = details.productName,
                    productStandard = productStandard,
                    fabricExpectedArrivalDate = fabricExpectedArrivalDate,
                    copperplateExpectedArrivalDate = copperplateExpectedArrivalDate,
                    productVendorName = productVendorName,
                    laminationVendorNames = laminationVendorNames,
                    processingVendorName = processingVendorName,
                ) as T

                else -> throw IllegalArgumentException("지원되지 않는 반환 클래스입니다.")
            }
        }
    }
}
