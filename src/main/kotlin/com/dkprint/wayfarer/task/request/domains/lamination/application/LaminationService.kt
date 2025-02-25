package com.dkprint.wayfarer.task.request.domains.lamination.application

import com.dkprint.wayfarer.task.request.domains.lamination.dao.LaminationRepository
import com.dkprint.wayfarer.task.request.domains.lamination.domain.Lamination
import com.dkprint.wayfarer.task.request.domains.lamination.dto.LaminationDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LaminationService(
    private val laminationRepository: LaminationRepository,
) {
    @Transactional
    fun create(
        taskRequestId: Long,
        laminationDtos: List<LaminationDto>,
    ) {
        laminationDtos.forEach { laminationDto ->
            val lamination: Lamination = Lamination.of(taskRequestId, laminationDto)
            laminationRepository.save(lamination)
        }
    }
}
