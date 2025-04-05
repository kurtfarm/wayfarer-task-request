package com.dkprint.wayfarer.task.request.slitting.dao

import com.dkprint.wayfarer.task.request.slitting.domain.Slitting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SlittingRepository : JpaRepository<Slitting, Long>
