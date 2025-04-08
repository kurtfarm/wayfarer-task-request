package com.dkprint.app.etc.application

import com.dkprint.app.etc.dao.EtcRepository
import com.dkprint.app.etc.domain.Etc
import com.dkprint.app.etc.dto.EtcDto
import org.springframework.stereotype.Service

@Service
class EtcService(
    private val etcRepository: EtcRepository,
    // TODO: private val vendorSdk: VendorSdk,
) {
    fun create(taskRequestId: Long, etcDto: EtcDto) {
        val vendorId: Long = 1L // TODO: vendorRepository.findIdByVendorName(etcDto.vendorName)
        val etc: Etc = Etc.of(taskRequestId, vendorId, etcDto)
        etcRepository.save(etc)
    }

    fun delete(taskRequestId: Long) {
        etcRepository.deleteByTaskRequestId(taskRequestId)
    }

    fun findEtc1(taskRequestId: Long): Etc {
        return etcRepository.findByTaskRequestIdAndEtcTypeIsFalse(taskRequestId)
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }

    fun findEtc2(taskRequestId: Long): Etc {
        return etcRepository.findByTaskRequestIdAndEtcTypeIsTrue(taskRequestId)
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }
}
