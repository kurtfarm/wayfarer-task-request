package com.dkprint.wayfarer.task.request.domains.details.application

import com.dkprint.wayfarer.task.request.domains.details.dao.DetailsRepository
import com.dkprint.wayfarer.task.request.domains.details.domain.Details
import com.dkprint.wayfarer.task.request.domains.details.dto.DetailsDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DetailsService(
    private val detailsRepository: DetailsRepository,
) {
    @Transactional
    fun create(
        taskRequestId: Long,
        detailsDto: DetailsDto,
    ) {
        val details: Details = Details.of(taskRequestId, detailsDto)
        detailsRepository.save(details)
    }
}
