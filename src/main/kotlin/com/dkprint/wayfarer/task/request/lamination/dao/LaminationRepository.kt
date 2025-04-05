package com.dkprint.wayfarer.task.request.lamination.dao

import com.dkprint.wayfarer.task.request.lamination.domain.Lamination
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LaminationRepository : JpaRepository<Lamination, Long> {
    fun deleteByTaskRequestId(taskRequestId: Long)
    fun findByTaskRequestId(taskRequestId: Long): List<Lamination>?
}
