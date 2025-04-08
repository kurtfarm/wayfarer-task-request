package com.dkprint.app.copperplate.mapping.application

import com.dkprint.app.copperplate.mapping.dao.CopperplateMappingRepository
import com.dkprint.app.copperplate.mapping.domain.CopperplateMapping
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
