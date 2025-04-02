package com.dkprint.wayfarer.task.request.domain.print.design.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "task_request_print_design")
class PrintDesign(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "task_request_id")
    val taskRequestId: Long,

    @Column(name = "print_design", columnDefinition = "TEXT")
    val printDesign: String,
)
