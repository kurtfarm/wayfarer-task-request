package com.dkprint.wayfarer.task.request.domains.etc.application

import com.dkprint.wayfarer.task.request.domains.etc.dao.EtcRepository
import com.dkprint.wayfarer.task.request.domains.etc.domain.Etc
import com.dkprint.wayfarer.task.request.domains.etc.dto.EtcDto
import org.springframework.stereotype.Service

@Service
class EtcService(
    private val etcRepository: EtcRepository,
    // private val vendorSdk: VendorSdk,
) {
    fun create(taskRequestId: Long, etcDto: EtcDto) {
        val vendorId: Long = 1L // vendorRepository.findByVendorName(etcDto.vendorName)
        val etc: Etc = Etc.of(taskRequestId, vendorId, etcDto)
        etcRepository.save(etc)
    }

    fun delete(taskRequestId: Long) {
        etcRepository.deleteById(taskRequestId)
    }
}
