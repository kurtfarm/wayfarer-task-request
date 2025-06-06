package com.dkprint.app.lamination.application

import com.dkprint.app.lamination.dao.LaminationRepository
import com.dkprint.app.lamination.domain.Lamination
import com.dkprint.app.lamination.dto.LaminationDto
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
        laminationRepository.deleteByTaskRequestId(taskRequestId)
    }

    fun find(taskRequestId: Long): List<Lamination> {
        return laminationRepository.findByTaskRequestId(taskRequestId)
            ?: throw IllegalArgumentException("작업 의뢰서 id: $taskRequestId 조회 오류")
    }
}
