package com.dkprint.wayfarer.task.request.domain.copperplate.mapping.application

import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.dao.CopperplateMappingRepository
import com.dkprint.wayfarer.task.request.domain.copperplate.mapping.domain.CopperplateMapping
import org.springframework.stereotype.Service

@Service
class CopperplateMappingService(
    private val copperplateMappingRepository: CopperplateMappingRepository,
) {
    fun create(copperplateMapping: CopperplateMapping) {
        copperplateMappingRepository.save(copperplateMapping)
    }

    fun findByCopperplateIdIn(copperplateIds: List<Long>): List<CopperplateMapping> {
        return copperplateMappingRepository.findByCopperplateIdIn(copperplateIds)
    }
}
