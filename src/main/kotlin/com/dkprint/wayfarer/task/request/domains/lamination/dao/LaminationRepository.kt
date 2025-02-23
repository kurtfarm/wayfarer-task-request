package com.dkprint.wayfarer.task.request.domains.lamination.dao

import com.dkprint.wayfarer.task.request.domains.lamination.domain.Lamination
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LaminationRepository : JpaRepository<Lamination, Long>
