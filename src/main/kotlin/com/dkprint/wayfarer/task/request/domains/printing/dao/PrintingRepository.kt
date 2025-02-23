package com.dkprint.wayfarer.task.request.domains.printing.dao

import com.dkprint.wayfarer.task.request.domains.printing.domain.Printing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrintingRepository : JpaRepository<Printing, Long>
