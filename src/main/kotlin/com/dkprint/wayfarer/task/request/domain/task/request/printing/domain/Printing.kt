package com.dkprint.wayfarer.task.request.domain.task.request.printing.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "task_request_printing")
class Printing(

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0L
}
