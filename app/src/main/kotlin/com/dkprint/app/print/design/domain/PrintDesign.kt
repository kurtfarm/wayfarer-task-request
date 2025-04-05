package com.dkprint.app.print.design.domain

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
    var taskRequestId: Long,

    @Column(name = "print_design", columnDefinition = "TEXT")
    var printDesign: String,
)
