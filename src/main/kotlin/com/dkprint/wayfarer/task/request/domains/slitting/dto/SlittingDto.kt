package com.dkprint.wayfarer.task.request.domains.slitting.dto

import java.time.LocalDate

data class SlittingDto(
    val slittingWidth: Int,
    val slittingLength: Int,
    val quantity: Long,
    val dueDate: LocalDate,
    val vendorName: String,
)
