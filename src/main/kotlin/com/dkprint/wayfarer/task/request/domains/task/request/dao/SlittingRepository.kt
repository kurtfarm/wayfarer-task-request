package com.dkprint.wayfarer.task.request.domains.task.request.dao

import com.dkprint.wayfarer.task.request.domains.task.request.domain.Slitting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SlittingRepository : JpaRepository<Slitting, Long>
