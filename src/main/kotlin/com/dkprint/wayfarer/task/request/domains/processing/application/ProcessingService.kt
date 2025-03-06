package com.dkprint.wayfarer.task.request.domains.processing.application

import com.dkprint.wayfarer.task.request.domains.processing.dao.ProcessingRepository
import com.dkprint.wayfarer.task.request.domains.processing.domain.Processing
import com.dkprint.wayfarer.task.request.domains.processing.dto.ProcessingDto
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class ProcessingService(
    private val processingRepository: ProcessingRepository,
    // private val vendorSdk: VendorSdk,
) {
    fun create(taskRequestId: Long, processingDto: ProcessingDto) {
        val vendorId: Long = 1L // vendorRepository.findIdByVendorName(processingDto.vendorName)
        val processing: Processing = Processing.of(taskRequestId, vendorId, processingDto)
        processingRepository.save(processing)
    }

    fun delete(taskRequestId: Long) {
        processingRepository.deleteById(taskRequestId)
    }

    fun find(taskRequestId: Long): Processing {
        return processingRepository.findById(taskRequestId).getOrNull()
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }
}
