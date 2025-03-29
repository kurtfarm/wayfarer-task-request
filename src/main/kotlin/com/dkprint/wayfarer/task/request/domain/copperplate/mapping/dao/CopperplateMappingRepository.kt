package com.dkprint.wayfarer.task.request.domain.copperplate.mapping.dao

import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.domain.CopperplateMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CopperplateMappingRepository : JpaRepository<CopperplateMapping, Long> {
    fun findByCopperplateIdIn(copperplateId: List<Long>): List<CopperplateMapping>
}
