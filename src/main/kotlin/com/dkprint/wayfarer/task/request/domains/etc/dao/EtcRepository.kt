package com.dkprint.wayfarer.task.request.domains.etc.dao

import com.dkprint.wayfarer.task.request.domains.etc.domain.Etc
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EtcRepository : JpaRepository<Etc, Long>
