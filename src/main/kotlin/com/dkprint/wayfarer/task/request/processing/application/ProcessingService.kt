package com.dkprint.wayfarer.task.request.processing.application

import com.dkprint.wayfarer.task.request.processing.dao.ProcessingRepository
import com.dkprint.wayfarer.task.request.processing.domain.Processing
import com.dkprint.wayfarer.task.request.processing.dto.ProcessingDto
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class ProcessingService(
    private val processingRepository: ProcessingRepository,
    // TODO: private val vendorSdk: VendorSdk,
) {
    fun create(taskRequestId: Long, processingDto: ProcessingDto) {
        val vendorId: Long = 1L // TODO: vendorRepository.findIdByVendorName(processingDto.vendorName)
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
