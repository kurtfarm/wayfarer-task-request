package com.dkprint.app.processing.dao

import com.dkprint.app.processing.domain.Processing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessingRepository : JpaRepository<Processing, Long>
