package com.dkprint.wayfarer.task.request.domains.vendor.domain

import com.dkprint.wayfarer.task.request.domains.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "vendor")
class Vendor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0L,

    @Column(name = "vendor_trade_history_id")
    var vendorTradeHistoryId: Long = 0L,

    @Column(name = "vendor_name")
    var vendorName: String,

    @Column(name = "copperplate_location")
    var copperplateLocation: String,

    @Column(name = "contact")
    var contact: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "comment")
    var comment: String,
) : BaseTimeEntity()
