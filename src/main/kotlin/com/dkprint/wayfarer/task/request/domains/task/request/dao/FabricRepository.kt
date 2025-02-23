package com.dkprint.wayfarer.task.request.domains.task.request.dao

import com.dkprint.wayfarer.task.request.domains.fabric.domain.Fabric
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FabricRepository : JpaRepository<Fabric, Long> {
    fun findByFabricType(fabricType: String): Fabric
}
