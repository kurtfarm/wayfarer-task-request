package com.dkprint.wayfarer.task.request.domains.etc.dto

import java.time.LocalDate

data class EtcDto(
    val taskName: String,
    val taskType: String,
    val quantity: Int,
    val dueDate: LocalDate,
    val vendorName: String,
    val etcType: Boolean,
)
