package com.dkprint.wayfarer.task.request.domains.lamination.application

import com.dkprint.wayfarer.task.request.domains.lamination.dao.LaminationRepository
import com.dkprint.wayfarer.task.request.domains.lamination.domain.Lamination
import com.dkprint.wayfarer.task.request.domains.lamination.dto.LaminationDto
import org.springframework.stereotype.Service

@Service
class LaminationService(
    private val laminationRepository: LaminationRepository,
) {
    fun create(taskRequestId: Long, laminationDto: LaminationDto) {
        val lamination: Lamination = Lamination.of(taskRequestId, laminationDto)
        laminationRepository.save(lamination)
    }

    fun delete(taskRequestId: Long) {
        laminationRepository.deleteAllByTaskRequestId(taskRequestId)
    }
}
