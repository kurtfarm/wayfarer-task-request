package com.dkprint.wayfarer.task.request.domains.print.design.dao

import com.dkprint.wayfarer.task.request.domains.print.design.domain.PrintDesign
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrintDesignRepository : JpaRepository<PrintDesign, Long> {
    fun deleteByTaskRequestId(taskRequestId: Long)
    fun findByTaskRequestId(taskRequestId: Long): List<PrintDesign>
}
