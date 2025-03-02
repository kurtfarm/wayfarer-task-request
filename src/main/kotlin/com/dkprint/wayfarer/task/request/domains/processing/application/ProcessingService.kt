package com.dkprint.wayfarer.task.request.domains.processing.application

import com.dkprint.wayfarer.task.request.domains.processing.dao.ProcessingRepository
import com.dkprint.wayfarer.task.request.domains.processing.domain.Processing
import com.dkprint.wayfarer.task.request.domains.processing.dto.ProcessingDto
import org.springframework.stereotype.Service

@Service
class ProcessingService(
    private val processingRepository: ProcessingRepository,
    // private val vendorSdk: VendorSdk,
) {
    fun create(taskRequestId: Long, processingDto: ProcessingDto) {
        val vendorId: Long = 1L // vendorRepository.findByVendorName(processingDto.vendorName)
        val processing: Processing = Processing.of(taskRequestId, vendorId, processingDto)
        processingRepository.save(processing)
    }

    fun delete(taskRequestId: Long) {
        processingRepository.deleteById(taskRequestId)
    }
}
