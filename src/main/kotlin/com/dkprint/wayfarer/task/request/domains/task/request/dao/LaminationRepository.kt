package com.dkprint.wayfarer.task.request.domains.task.request.dao

import com.dkprint.wayfarer.task.request.domains.task.request.domain.Lamination
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LaminationRepository : JpaRepository<Lamination, Long>
