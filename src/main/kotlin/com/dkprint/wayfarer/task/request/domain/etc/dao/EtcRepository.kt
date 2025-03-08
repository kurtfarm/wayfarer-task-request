package com.dkprint.wayfarer.task.request.domain.etc.dao

import com.dkprint.wayfarer.task.request.domain.etc.domain.Etc
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EtcRepository : JpaRepository<Etc, Long> {
    fun findByTaskRequestIdAndEtcTypeIsFalse(taskRequestId: Long): Etc?
    fun findByTaskRequestIdAndEtcTypeIsTrue(taskRequestId: Long): Etc?
}
