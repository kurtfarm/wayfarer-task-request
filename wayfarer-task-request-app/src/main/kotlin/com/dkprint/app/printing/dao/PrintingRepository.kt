package com.dkprint.app.printing.dao

import com.dkprint.app.printing.domain.Printing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrintingRepository : JpaRepository<Printing, Long>
