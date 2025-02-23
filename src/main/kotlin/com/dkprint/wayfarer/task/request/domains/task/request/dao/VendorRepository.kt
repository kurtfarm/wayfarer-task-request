package com.dkprint.wayfarer.task.request.domains.task.request.dao

import com.dkprint.wayfarer.task.request.domains.vendor.domain.Vendor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VendorRepository : JpaRepository<Vendor, Long> {
    fun findByVendorName(vendorName: String): Vendor?
}
