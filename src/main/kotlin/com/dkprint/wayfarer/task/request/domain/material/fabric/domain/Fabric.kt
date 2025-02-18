package com.dkprint.wayfarer.task.request.domain.material.fabric.domain

import com.dkprint.wayfarer.task.request.domain.model.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "material_fabric")
class Fabric(
    @Column(name = "fabric_class")
    private val fabricClass: String,

    @Column(name = "fabric_type")
    private val fabricType: String,

    @Column(name = "standard")
    private val standard: Int,

    @Column(name = "width")
    private val width: Int,

    @Column(name = "quantity")
    private val quantity: Int,

    @Column(name = "eta")
    private val eta: LocalDate,

    @Column(name = "status")
    private val status: Boolean
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0L

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
