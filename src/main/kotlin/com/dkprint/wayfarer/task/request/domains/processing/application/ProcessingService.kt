package com.dkprint.wayfarer.task.request.domains.processing.application

import com.dkprint.wayfarer.task.request.domains.processing.dao.ProcessingRepository
import com.dkprint.wayfarer.task.request.domains.processing.domain.Processing
import com.dkprint.wayfarer.task.request.domains.processing.dto.ProcessingDto
import com.dkprint.wayfarer.task.request.domains.vendor.dao.VendorRepository
import com.dkprint.wayfarer.task.request.domains.vendor.domain.Vendor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProcessingService(
    private val processingRepository: ProcessingRepository,
    private val vendorRepository: VendorRepository,
) {
    @Transactional
    fun createProcessing(
        taskRequestId: Long,
        processingDto: ProcessingDto,
    ) {
        val vendor: Vendor = vendorRepository.findByVendorName(processingDto.vendorName)
            ?: throw IllegalArgumentException("거래처: ${processingDto.vendorName} 조회 오류")
        val processing: Processing = Processing.of(taskRequestId, vendor.id, processingDto)
        processingRepository.save(processing)
    }
}
