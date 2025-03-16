package com.dkprint.wayfarer.task.request.domain.details.application

import com.dkprint.wayfarer.task.request.domain.details.dao.DetailsRepository
import com.dkprint.wayfarer.task.request.domain.details.domain.Details
import com.dkprint.wayfarer.task.request.domain.details.dto.DetailsDto
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class DetailsService(
    private val detailsRepository: DetailsRepository,
    // private val vendorSdk: VendorSdk,
) {
    fun create(taskRequestId: Long, detailsDto: DetailsDto) {
        val vendorId: Long = 1L // vendorSdk.findIdByName(detailsDto.vendorName)
        val details: Details = Details.of(taskRequestId, vendorId, detailsDto)
        detailsRepository.save(details)
    }

    fun delete(taskRequestId: Long) {
        detailsRepository.deleteById(taskRequestId)
    }

    fun find(taskRequestId: Long): Details {
        return detailsRepository.findById(taskRequestId).getOrNull()
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }
}
