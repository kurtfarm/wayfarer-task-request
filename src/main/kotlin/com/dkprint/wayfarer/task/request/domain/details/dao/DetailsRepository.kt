package com.dkprint.wayfarer.task.request.domain.details.dao

import com.dkprint.wayfarer.task.request.domain.details.domain.Details
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetailsRepository : JpaRepository<Details, Long> {
    fun findByProductNameContainingIgnoreCase(productName: String, pageable: Pageable): Page<Details>
    fun findByStandardWidthAndStandardLengthAndStandardHeight(
        width: Int,
        length: Int,
        height: Int,
        pageable: Pageable,
    ): Page<Details>
    fun findByVendorId(vendorId: Long, pageable: Pageable): Page<Details>
}
