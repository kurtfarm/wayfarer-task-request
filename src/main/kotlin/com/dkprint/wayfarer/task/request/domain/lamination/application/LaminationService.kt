package com.dkprint.wayfarer.task.request.domain.lamination.application

import com.dkprint.wayfarer.task.request.domain.lamination.dao.LaminationRepository
import com.dkprint.wayfarer.task.request.domain.lamination.domain.Lamination
import com.dkprint.wayfarer.task.request.domain.lamination.dto.LaminationDto
import org.springframework.stereotype.Service

@Service
class LaminationService(
    private val laminationRepository: LaminationRepository,
) {
    fun create(taskRequestId: Long, laminationDto: LaminationDto) {
        val vendorId: Long = 1L // TODO: vendorSdk.findIdByVendorName(laminationDto.taskVendorName)
        val lamination: Lamination = Lamination.of(taskRequestId, laminationDto, vendorId)
        laminationRepository.save(lamination)
    }

    fun delete(taskRequestId: Long) {
        laminationRepository.deleteAllByTaskRequestId(taskRequestId)
    }

    fun find(taskRequestId: Long): List<Lamination> {
        return laminationRepository.findByTaskRequestId(taskRequestId)
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }
}
