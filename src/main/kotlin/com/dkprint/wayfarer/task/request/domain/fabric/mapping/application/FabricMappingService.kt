package com.dkprint.wayfarer.task.request.domain.fabric.mapping.application

import com.dkprint.wayfarer.task.request.domain.fabric.mapping.dao.FabricMappingRepository
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.domain.FabricMapping
import com.dkprint.wayfarer.task.request.domain.fabric.mapping.dto.FabricDto
import org.springframework.stereotype.Service

@Service
class FabricMappingService(
    private val fabricMappingRepository: FabricMappingRepository,
    // private val fabricSdk: FabricSdk,
) {
    fun create(taskRequestId: Long, fabricDto: FabricDto) {
        val fabricId: Long = 1L // fabricSdk.findIdByFabricType(fabricDto.fabricType)
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
