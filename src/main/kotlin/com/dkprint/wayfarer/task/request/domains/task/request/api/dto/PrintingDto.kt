package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDate
import java.time.LocalDateTime

class PrintingDto(
    val numberOfInks: String,
    val width: Int,
    val height: Int,
    val dueDate: LocalDate,
    val printingType: String,
    val supervisionDate: LocalDateTime,
    val printingDirection: Int,
    val copperplateId: Long
)
