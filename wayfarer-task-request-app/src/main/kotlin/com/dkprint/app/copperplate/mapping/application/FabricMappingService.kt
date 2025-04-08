package com.dkprint.app.copperplate.mapping.application

import com.dkprint.app.copperplate.mapping.dao.FabricMappingRepository
import com.dkprint.app.copperplate.mapping.domain.FabricMapping
import com.dkprint.app.copperplate.mapping.dto.FabricDto
import org.springframework.stereotype.Service

@Service
class FabricMappingService(
    private val fabricMappingRepository: FabricMappingRepository,
    // TODO: private val fabricSdk: FabricSdk,
) {
    fun create(taskRequestId: Long, fabricDto: FabricDto) {
        val fabricId: Long = 1L // TODO: fabricSdk.findIdByFabricType(fabricDto.fabricType)
        val fabricMapping: FabricMapping = FabricMapping.of(taskRequestId, fabricId, fabricDto.fabricClass)
        fabricMappingRepository.save(fabricMapping)
    }

    fun delete(taskRequestId: Long) {
        fabricMappingRepository.deleteAllByTaskRequestId(taskRequestId)
    }

    fun findByTaskRequestId(taskRequestId: Long): List<FabricMapping> {
        return fabricMappingRepository.findByTaskRequestId(taskRequestId)
    }

    fun findByFabricIdIn(fabricIds: List<Long>): List<FabricMapping> {
        return fabricMappingRepository.findByFabricIdIn(fabricIds)
    }
}
