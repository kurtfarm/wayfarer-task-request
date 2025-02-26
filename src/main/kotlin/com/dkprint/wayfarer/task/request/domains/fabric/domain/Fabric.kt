package com.dkprint.wayfarer.task.request.domains.fabric.domain

import com.dkprint.wayfarer.task.request.domains.common.domain.BaseTimeEntity
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0L,

    @Column(name = "code_id")
    var codeId: Long = 0L,

    @Column(name = "expected_arrival_date")
    var expectedArrivalDate: LocalDate,

    @Column(name = "fabric_type")
    var fabricType: String,

    @Column(name = "orderer_id")
    var ordererId: Long = 0L,

    @Column(name = "customer_id")
    var customerId: Long = 0L,

    @Column(name = "fabric_class")
    var fabricClass: String,

    @Column(name = "standard_length")
    var standardLength: Int,

    @Column(name = "standard_width")
    var standardWidth: Int,

    @Column(name = "standard_thickness")
    var standardThickness: Int,

    @Column(name = "quantity")
    var quantity: Int,

    @Column(name = "comment")
    var comment: String? = null,
) : BaseTimeEntity()

