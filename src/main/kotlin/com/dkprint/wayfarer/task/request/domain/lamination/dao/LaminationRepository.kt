package com.dkprint.wayfarer.task.request.domain.lamination.dao

import com.dkprint.wayfarer.task.request.domain.lamination.domain.Lamination
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LaminationRepository : JpaRepository<Lamination, Long> {
    fun deleteAllByTaskRequestId(taskRequestId: Long)
    fun findByTaskRequestId(taskRequestId: Long): List<Lamination>?
}
