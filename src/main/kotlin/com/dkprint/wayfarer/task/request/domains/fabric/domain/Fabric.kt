package com.dkprint.wayfarer.task.request.domains.fabric.domain

import com.dkprint.wayfarer.task.request.domains.model.BaseEntity
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
    @Column(name = "code_id")
    private var codeId: Long,

    @Column(name = "vendor_id")
    private var vendorId: Long,

    @Column(name = "fabric_class")
    private var fabricClass: String,

    @Column(name = "fabric_type")
    private var fabricType: String,

    @Column(name = "standard")
    private var standard: Int,

    @Column(name = "width")
    private var width: Int,

    @Column(name = "thickness")
    private var thickness: Int,

    @Column(name = "quantity")
    private var quantity: Int,

    @Column(name = "eta")
    private var eta: LocalDate,

    @Column(name = "status")
    private var status: Boolean
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
