package com.dkprint.wayfarer.task.request.domains.fabric.mapping.application

import com.dkprint.wayfarer.task.request.domains.fabric.dao.FabricRepository
import com.dkprint.wayfarer.task.request.domains.fabric.domain.Fabric
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.dao.FabricMappingRepository
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.domain.FabricMapping
import com.dkprint.wayfarer.task.request.domains.fabric.mapping.dto.FabricDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FabricMappingService(
    private val fabricMappingRepository: FabricMappingRepository,
    private val fabricRepository: FabricRepository,
) {
    @Transactional
    fun create(
        taskRequestId: Long,
        fabricDto: FabricDto,
    ) {
        val fabric: Fabric = fabricRepository.findByFabricType(fabricDto.fabricType)
            ?: throw IllegalArgumentException("fabric type: ${fabricDto.fabricType} 조회 오류")
        val fabricMapping: FabricMapping = FabricMapping.of(taskRequestId, fabric.id, fabricDto.fabricClass)
        fabricMappingRepository.save(fabricMapping)
    }
}
