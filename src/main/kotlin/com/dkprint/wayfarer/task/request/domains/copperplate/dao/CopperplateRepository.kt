package com.dkprint.wayfarer.task.request.domains.copperplate.dao

import com.dkprint.wayfarer.task.request.domains.copperplate.domain.Copperplate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CopperplateRepository : JpaRepository<Copperplate, Long> {
    fun findByCopperplateName(copperplateName: String): Copperplate?
}
