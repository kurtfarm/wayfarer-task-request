package com.dkprint.app.print.design.dao

import com.dkprint.app.print.design.domain.PrintDesign
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrintDesignRepository : JpaRepository<PrintDesign, Long> {
    fun deleteByTaskRequestId(taskRequestId: Long)
    fun findByTaskRequestId(taskRequestId: Long): List<PrintDesign>
}
