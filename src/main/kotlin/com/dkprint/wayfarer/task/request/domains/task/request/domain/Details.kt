package com.dkprint.wayfarer.task.request.domains.task.request.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_details")
class Details(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    private var taskRequest: TaskRequest,

    @Column(name = "order_date")
    private val orderDate: LocalDate,

    @Column(name = "product_name")
    private var productName: String,

    @Column(name = "product_type")
    private var productType: String,

    @Column(name = "due_date")
    private var dueDate: LocalDate,

    @Column(name = "standard")
    private var standard: Int,

    @Column(name = "width")
    private var width: Int,

    @Column(name = "thickness")
    private var thickness: Int,

    @Column(name = "sheets_count")
    private var sheetsCount: Int,

    @Column(name = "meter")
    private var meter: Int,

    @Column(name = "rolls_count")
    private var rollsCount: Int,

    @Column(name = "vendor_id")
    private var vendorId: Long
)
