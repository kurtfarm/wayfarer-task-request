package com.dkprint.app.fabric.mapping.dao

import com.dkprint.app.fabric.mapping.domain.FabricMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FabricMappingRepository : JpaRepository<FabricMapping, Long> {
    fun deleteAllByTaskRequestId(taskRequestId: Long)
    fun findByTaskRequestId(taskRequestId: Long): List<FabricMapping>
    fun findByFabricIdIn(fabricIds: List<Long>): List<FabricMapping>
}
