package com.dkprint.wayfarer.task.request.domains.fabric.mapping.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "task_request_material_fabric_mapping")
class FabricMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0L,

    @Column(name = "task_request_id")
    var taskRequestId: Long = 0L,

    @Column(name = "material_fabric_id")
    var fabricId: Long = 0L,

    @Column(name = "fabric_class")
    var fabricClass: Int = 0,
) {
    companion object {
        fun of(
            taskRequestId: Long,
            fabricId: Long,
            fabricClass: Int,
        ): FabricMapping {
            return FabricMapping(
                taskRequestId = taskRequestId,
                fabricId = fabricId,
                fabricClass = fabricClass,
            )
        }
    }
}
