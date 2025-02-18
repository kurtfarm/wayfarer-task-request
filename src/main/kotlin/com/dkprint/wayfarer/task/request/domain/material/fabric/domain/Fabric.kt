package com.dkprint.wayfarer.task.request.domain.material.fabric.domain

import com.dkprint.wayfarer.task.request.domain.model.BaseEntity
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "material_fabric")
class Fabric(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    private var taskRequest: TaskRequest,

    @Column(name = "fabric_class")
    private var fabricClass: String,

    @Column(name = "fabric_type")
    private var fabricType: String,

    @Column(name = "standard")
    private var standard: Int,

    @Column(name = "width")
    private var width: Int,

    @Column(name = "quantity")
    private var quantity: Int,

    @Column(name = "eta")
    private var eta: LocalDate,

    @Column(name = "status")
    private var status: Boolean
) : BaseEntity() {
    init {
        validate()
    }

    private fun validate() {
        require(width > 0) {
            "너비는 0보다 커야 합니다."
        }
        require(quantity > 0) {
            "수량은 0보다 커야 합니다."
        }
        require(eta.isAfter(LocalDate.now())) {
            "입고예정일은 현재 날짜 이후여야 합니다."
        }
    }
}
