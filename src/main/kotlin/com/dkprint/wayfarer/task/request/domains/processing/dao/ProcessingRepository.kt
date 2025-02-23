package com.dkprint.wayfarer.task.request.domains.processing.dao

import com.dkprint.wayfarer.task.request.domains.processing.domain.Processing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessingRepository : JpaRepository<Processing, Long>
