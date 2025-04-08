package com.dkprint.app.slitting.dao

import com.dkprint.app.slitting.domain.Slitting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SlittingRepository : JpaRepository<Slitting, Long>
