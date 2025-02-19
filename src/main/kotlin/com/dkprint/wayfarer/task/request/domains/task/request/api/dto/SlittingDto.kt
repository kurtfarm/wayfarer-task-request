package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDate

class SlittingDto(
    val slittingWidth: Int,
    val slittingHeight: Int,
    val quantity: Long,
    val dueDate: LocalDate,
    val vendorName: String
)
