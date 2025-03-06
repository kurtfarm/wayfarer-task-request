package com.dkprint.wayfarer.task.request.domains.details.application

import com.dkprint.wayfarer.task.request.domains.details.dao.DetailsRepository
import com.dkprint.wayfarer.task.request.domains.details.domain.Details
import com.dkprint.wayfarer.task.request.domains.details.dto.DetailsDto
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class DetailsService(
    private val detailsRepository: DetailsRepository,
) {
    fun create(taskRequestId: Long, detailsDto: DetailsDto) {
        val details: Details = Details.of(taskRequestId, detailsDto)
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
