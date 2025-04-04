package com.dkprint.wayfarer.task.request.domain.slitting.application

import com.dkprint.wayfarer.task.request.domain.slitting.dao.SlittingRepository
import com.dkprint.wayfarer.task.request.domain.slitting.domain.Slitting
import com.dkprint.wayfarer.task.request.domain.slitting.dto.SlittingDto
import org.springframework.stereotype.Service

@Service
class SlittingService(
    private val slittingRepository: SlittingRepository,
    // TODO: private val vendorSdk: VendorSdk,
) {
    fun create(taskRequestId: Long, slittingDto: SlittingDto) {
        val vendorId: Long = 1L // TODO: vendorSdk.findIdByVendorName(slittingDto.vendorName)
        val slitting: Slitting = Slitting.of(taskRequestId, vendorId, slittingDto)
        slittingRepository.save(slitting)
    }

    fun delete(taskRequestId: Long) {
        slittingRepository.deleteById(taskRequestId)
    }
}
