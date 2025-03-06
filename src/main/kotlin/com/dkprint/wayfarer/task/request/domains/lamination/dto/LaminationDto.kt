package com.dkprint.wayfarer.task.request.domains.lamination.dto

import java.time.LocalDate

data class LaminationDto(
    val sequence: Int,
    val taskVendorName: String,
    val taskType: String,
    val quantity: Int,
    val comment: String,
    val dueDate: LocalDate,
)
