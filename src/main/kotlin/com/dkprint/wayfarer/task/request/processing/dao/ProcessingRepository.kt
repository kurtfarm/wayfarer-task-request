package com.dkprint.wayfarer.task.request.processing.dao

import com.dkprint.wayfarer.task.request.processing.domain.Processing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessingRepository : JpaRepository<Processing, Long>
