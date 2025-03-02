package com.dkprint.wayfarer.task.request.domains.slitting.application

import com.dkprint.wayfarer.task.request.domains.slitting.dao.SlittingRepository
import com.dkprint.wayfarer.task.request.domains.slitting.domain.Slitting
import com.dkprint.wayfarer.task.request.domains.slitting.dto.SlittingDto
import org.springframework.stereotype.Service

@Service
class SlittingService(
    private val slittingRepository: SlittingRepository,
    // private val vendorSdk: VendorSdk,
) {
    fun create(taskRequestId: Long, slittingDto: SlittingDto) {
        val vendorId: Long = 1L // vendorSdk.findByVendorName(slittingDto.vendorName)
        val slitting: Slitting = Slitting.of(taskRequestId, vendorId, slittingDto)
        slittingRepository.save(slitting)
    }

    fun delete(taskRequestId: Long) {
        slittingRepository.deleteById(taskRequestId)
    }
}
