package com.dkprint.wayfarer.task.request.domains.task.request.application

import com.dkprint.wayfarer.task.request.domains.copperplate.domain.Copperplate
import com.dkprint.wayfarer.task.request.domains.fabric.domain.Fabric
import com.dkprint.wayfarer.task.request.domains.details.dto.DetailsDto
import com.dkprint.wayfarer.task.request.domains.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.dto.FabricDto
import com.dkprint.wayfarer.task.request.domains.lamination.dto.LaminationDto
import com.dkprint.wayfarer.task.request.domains.printing.dto.PrintingDto
import com.dkprint.wayfarer.task.request.domains.processing.dto.ProcessingDto
import com.dkprint.wayfarer.task.request.domains.slitting.dto.SlittingDto
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestDto
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestResponse
import com.dkprint.wayfarer.task.request.domains.copperplate.dao.CopperplateRepository
import com.dkprint.wayfarer.task.request.domains.details.dao.DetailsRepository
import com.dkprint.wayfarer.task.request.domains.etc.dao.EtcRepository
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.dao.FabricMappingRepository
import com.dkprint.wayfarer.task.request.domains.fabric.dao.FabricRepository
import com.dkprint.wayfarer.task.request.domains.lamination.dao.LaminationRepository
import com.dkprint.wayfarer.task.request.domains.printing.dao.PrintingRepository
import com.dkprint.wayfarer.task.request.domains.processing.dao.ProcessingRepository
import com.dkprint.wayfarer.task.request.domains.slitting.dao.SlittingRepository
import com.dkprint.wayfarer.task.request.domains.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domains.vendor.dao.VendorRepository
import com.dkprint.wayfarer.task.request.domains.details.domain.Details
import com.dkprint.wayfarer.task.request.domains.etc.domain.Etc
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.domain.FabricMapping
import com.dkprint.wayfarer.task.request.domains.lamination.domain.Lamination
import com.dkprint.wayfarer.task.request.domains.printing.domain.Printing
import com.dkprint.wayfarer.task.request.domains.processing.domain.Processing
import com.dkprint.wayfarer.task.request.domains.slitting.domain.Slitting
import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import com.dkprint.wayfarer.task.request.domains.vendor.domain.Vendor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,
    private val detailsRepository: DetailsRepository,
    private val fabricMappingRepository: FabricMappingRepository,
    private val printingRepository: PrintingRepository,
    private val laminationRepository: LaminationRepository,
    private val slittingRepository: SlittingRepository,
    private val etcRepository: EtcRepository,
    private val processingRepository: ProcessingRepository,

    private val fabricRepository: FabricRepository,
    private val vendorRepository: VendorRepository,
    private val copperplateRepository: CopperplateRepository,
) {
    @Transactional
    fun create(taskRequestDto: TaskRequestDto): TaskRequestResponse {
        val savedTaskRequest: TaskRequest = createTaskRequest(taskRequestDto)
        createDetails(savedTaskRequest.id, taskRequestDto.detailsDto)
        createFabricMapping(savedTaskRequest.id, taskRequestDto.fabricDtos)

        taskRequestDto.printingDto?.let { createPrinting(savedTaskRequest.id, taskRequestDto.printingDto) }
        taskRequestDto.laminationDtos?.let { createLamination(savedTaskRequest.id, taskRequestDto.laminationDtos) }
        taskRequestDto.slittingDto?.let { createSlitting(savedTaskRequest.id, taskRequestDto.slittingDto) }
        taskRequestDto.etc1Dto?.let { createEtc(savedTaskRequest.id, taskRequestDto.etc1Dto) }
        taskRequestDto.etc2Dto?.let { createEtc(savedTaskRequest.id, taskRequestDto.etc2Dto) }
        taskRequestDto.processingDto?.let { createProcessing(savedTaskRequest.id, taskRequestDto.processingDto) }

        return TaskRequestResponse(
            id = savedTaskRequest.id,
            status = true,
        )
    }

    private fun createTaskRequest(
        taskRequestDto: TaskRequestDto,
    ): TaskRequest {
        val taskRequest: TaskRequest = TaskRequest.from(taskRequestDto)
        val savedRequest: TaskRequest = taskRequestRepository.save(taskRequest)
        return savedRequest
    }

    private fun createDetails(
        taskRequestId: Long,
        detailsDto: DetailsDto,
    ) {
        val details: Details = Details.of(taskRequestId, detailsDto)
        detailsRepository.save(details)
    }


    private fun createFabricMapping(
        taskRequestId: Long,
        fabricDtos: List<FabricDto>,
    ) {
        fabricDtos.forEach { fabricDto ->
            val fabric: Fabric = fabricRepository.findByFabricType(fabricDto.fabricType)
            val fabricMapping: FabricMapping = FabricMapping.of(taskRequestId, fabric.id, fabricDto.fabricClass)
            fabricMappingRepository.save(fabricMapping)
        }
    }

    private fun createPrinting(
        taskRequestId: Long,
        printingDto: PrintingDto,
    ) {
        val copperplate: Copperplate = copperplateRepository.findByCopperplateName(printingDto.copperplateName)
            ?: throw IllegalArgumentException("동판: ${printingDto.copperplateName} 조회 오류")
        val printing: Printing = Printing.of(taskRequestId, copperplate.id, printingDto)
        printingRepository.save(printing)
    }

    private fun createLamination(
        taskRequestId: Long,
        laminationDtos: List<LaminationDto>,
    ) {
        laminationDtos.forEach { laminationDto ->
            val lamination: Lamination = Lamination.of(taskRequestId, laminationDto)
            laminationRepository.save(lamination)
        }
    }

    private fun createSlitting(
        taskRequestId: Long,
        slittingDto: SlittingDto,
    ) {
        val vendor: Vendor = vendorRepository.findByVendorName(slittingDto.vendorName)
            ?: throw IllegalArgumentException("거래처: ${slittingDto.vendorName} 조회 오류")
        val slitting: Slitting = Slitting.of(taskRequestId, vendor.id, slittingDto)
        slittingRepository.save(slitting)
    }

    private fun createEtc(
        taskRequestId: Long,
        etcDto: EtcDto,
    ) {
        val vendor: Vendor = vendorRepository.findByVendorName(etcDto.vendorName)
            ?: throw IllegalArgumentException("거래처: ${etcDto.vendorName} 조회 오류")
        val etc: Etc = Etc.of(taskRequestId, vendor.id, etcDto)
        etcRepository.save(etc)
    }

    private fun createProcessing(
        taskRequestId: Long,
        processingDto: ProcessingDto,
    ) {
        val vendor: Vendor = vendorRepository.findByVendorName(processingDto.vendorName)
            ?: throw IllegalArgumentException("거래처: ${processingDto.vendorName} 조회 오류")
        val processing: Processing = Processing.of(taskRequestId, vendor.id, processingDto)
        processingRepository.save(processing)
    }
}
