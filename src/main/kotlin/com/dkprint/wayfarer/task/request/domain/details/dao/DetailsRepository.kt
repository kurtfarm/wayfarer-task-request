package com.dkprint.wayfarer.task.request.domain.details.dao

import com.dkprint.wayfarer.task.request.domain.details.domain.Details
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetailsRepository : JpaRepository<Details, Long>
