package com.dkprint.wayfarer.task.request.domains.task.request.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_etc")
class Etc(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    var taskRequest: TaskRequest,

    @Column(name = "task_name")
    private var taskName: String,

    @Column(name = "task_type")
    private var taskType: String,

    @Column(name = "quantity")
    private var quantity: Int,

    @Column(name = "due_date")
    private var dueDate: LocalDate,

    @Column(name = "vendor_id")
    private var vendorId: Long,

    @Column(name = "etc_type")
    private var etcType: Boolean
)
