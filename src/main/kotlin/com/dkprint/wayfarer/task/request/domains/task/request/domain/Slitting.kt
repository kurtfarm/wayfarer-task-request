package com.dkprint.wayfarer.task.request.domains.task.request.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_slitting")
class Slitting(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    private var taskRequest: TaskRequest,

    @Column(name = "slitting_width")
    private var slittingWidth: Int,

    @Column(name = "slitting_height")
    private var slittingHeight: Int,

    @Column(name = "quantity")
    private var quantity: Long,

    @Column(name = "due_date")
    private var dueDate: LocalDate,

    @Column(name = "vendor_id")
    private var vendorId: Long
)
