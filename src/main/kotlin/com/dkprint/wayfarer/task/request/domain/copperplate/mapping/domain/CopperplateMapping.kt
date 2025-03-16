package com.dkprint.wayfarer.task.request.domain.copperplate.mapping.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "task_request_material_copperplate_mapping")
class CopperplateMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0L,

    @Column(name = "task_request_id", nullable = false)
    var taskRequestId: Long,

    @Column(name = "copperplate_id", nullable = false)
    var copperplateId: Long,
) {
    companion object {
        fun of(taskRequestId: Long, copperplateId: Long): CopperplateMapping {
            return CopperplateMapping(
                taskRequestId = taskRequestId,
                copperplateId = copperplateId,
            )
        }
    }
}
