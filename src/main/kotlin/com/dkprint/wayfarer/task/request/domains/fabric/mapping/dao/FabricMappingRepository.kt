package com.dkprint.wayfarer.task.request.domains.fabric.mapping.dao

import com.dkprint.wayfarer.task.request.domains.fabric.mapping.domain.FabricMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FabricMappingRepository : JpaRepository<FabricMapping, Long>
