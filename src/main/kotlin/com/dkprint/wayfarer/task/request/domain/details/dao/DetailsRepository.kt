package com.dkprint.wayfarer.task.request.domain.details.dao

import com.dkprint.wayfarer.task.request.domain.details.domain.Details
import java.time.LocalDate
import java.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetailsRepository : JpaRepository<Details, Long> {
    fun findTop20ByTaskRequestIdGreaterThanAndProductNameContainingIgnoreCase(
        lastId: Long,
        productName: String,
    ): List<Details>

    fun findTop20ByTaskRequestIdGreaterThanAndStandardWidthAndStandardLengthAndStandardHeight(
        lastId: Long,
        width: Int,
        length: Int,
        height: Int,
    ): List<Details>

    fun findTop20ByTaskRequestIdGreaterThanAndVendorId(
        lastId: Long,
        vendorId: Long,
    ): List<Details>

    fun findTop20ByTaskRequestIdGreaterThanAndOrderDateBetweenOrderByTaskRequestIdAsc(
        taskRequestId: Long,
        start: LocalDate,
        end: LocalDate,
    ): List<Details>
}
