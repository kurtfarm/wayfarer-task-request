package com.dkprint.wayfarer.task.request.domains.etc.application

import com.dkprint.wayfarer.task.request.domains.etc.dao.EtcRepository
import com.dkprint.wayfarer.task.request.domains.etc.domain.Etc
import com.dkprint.wayfarer.task.request.domains.etc.dto.EtcDto
import com.dkprint.wayfarer.task.request.domains.vendor.dao.VendorRepository
import com.dkprint.wayfarer.task.request.domains.vendor.domain.Vendor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EtcService(
    private val etcRepository: EtcRepository,
    private val vendorRepository: VendorRepository,
) {
    @Transactional
    fun create(
        taskRequestId: Long,
        etcDto: EtcDto,
    ) {
        val vendor: Vendor = vendorRepository.findByVendorName(etcDto.vendorName)
            ?: throw IllegalArgumentException("거래처: ${etcDto.vendorName} 조회 오류")
        val etc: Etc = Etc.of(taskRequestId, vendor.id, etcDto)
        etcRepository.save(etc)
    }
}
