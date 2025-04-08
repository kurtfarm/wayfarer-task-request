package com.dkprint.app.lamination.dao

import com.dkprint.app.lamination.domain.Lamination
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LaminationRepository : JpaRepository<Lamination, Long> {
    fun deleteByTaskRequestId(taskRequestId: Long)
    fun findByTaskRequestId(taskRequestId: Long): List<Lamination>?
}
