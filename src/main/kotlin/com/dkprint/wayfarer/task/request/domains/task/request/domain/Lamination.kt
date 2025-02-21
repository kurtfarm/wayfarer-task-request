package com.dkprint.wayfarer.task.request.domains.task.request.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_lamination")
class Lamination(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    private var taskRequest: TaskRequest,

    @Column(name = "sequence")
    private var sequence: Int,

    @Column(name = "task_name")
    private var taskName: String,

    @Column(name = "task_type")
    private var taskType: String,

    @Column(name = "quantity")
    private var quantity: Int,

    @Column(name = "comment")
    private var comment: String,

    @Column(name = "due_date")
    private var dueDate: LocalDate
)
