package com.dkprint.wayfarer.task.request.domains.slitting.application

import com.dkprint.wayfarer.task.request.domains.slitting.dao.SlittingRepository
import com.dkprint.wayfarer.task.request.domains.slitting.domain.Slitting
import com.dkprint.wayfarer.task.request.domains.slitting.dto.SlittingDto
import com.dkprint.wayfarer.task.request.domains.vendor.dao.VendorRepository
import com.dkprint.wayfarer.task.request.domains.vendor.domain.Vendor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SlittingService(
    private val slittingRepository: SlittingRepository,
    private val vendorRepository: VendorRepository,
) {
    @Transactional
    fun create(
        taskRequestId: Long,
        slittingDto: SlittingDto,
    ) {
        val vendor: Vendor = vendorRepository.findByVendorName(slittingDto.vendorName)
            ?: throw IllegalArgumentException("거래처: ${slittingDto.vendorName} 조회 오류")
        val slitting: Slitting = Slitting.of(taskRequestId, vendor.id, slittingDto)
        slittingRepository.save(slitting)
    }
}
