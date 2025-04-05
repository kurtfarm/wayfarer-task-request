package com.dkprint.wayfarer.task.request.details.dao

import com.dkprint.wayfarer.task.request.details.domain.Details
import java.time.LocalDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetailsRepository : JpaRepository<Details, Long> {
    fun findByProductNameContainingIgnoreCase(productName: String): List<Details>

    fun findByStandardWidthAndStandardLengthAndStandardHeight(
        width: Int,
        length: Int,
        height: Int,
    ): List<Details>

    fun findByVendorId(vendorId: Long): List<Details>

    fun findByOrderDateBetween(startDate: LocalDate, endDate: LocalDate): List<Details>
}
