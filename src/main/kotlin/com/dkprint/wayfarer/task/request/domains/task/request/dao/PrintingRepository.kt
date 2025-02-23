package com.dkprint.wayfarer.task.request.domains.task.request.dao

import com.dkprint.wayfarer.task.request.domains.task.request.domain.Printing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrintingRepository : JpaRepository<Printing, Long>
