package com.dkprint.wayfarer.task.request.domains.lamination.dto

import java.time.LocalDate

data class LaminationDto(
    val sequence: Int,
    val taskName: String,
    val taskType: String,
    val quantity: Int,
    val comment: String,
    val dueDate: LocalDate,
)
