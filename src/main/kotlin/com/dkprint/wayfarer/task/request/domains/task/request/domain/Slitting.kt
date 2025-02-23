package com.dkprint.wayfarer.task.request.domains.task.request.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_slitting")
class Slitting(
    @Id
    @Column(name = "task_request_id")
    var taskRequestId: Long,

    @Column(name = "slitting_width")
    var slittingWidth: Int,

    @Column(name = "slitting_height")
    var slittingHeight: Int,

    @Column(name = "quantity")
    var quantity: Long,

    @Column(name = "due_date")
    var dueDate: LocalDate,

    @Column(name = "vendor_id")
    var vendorId: Long,
)
