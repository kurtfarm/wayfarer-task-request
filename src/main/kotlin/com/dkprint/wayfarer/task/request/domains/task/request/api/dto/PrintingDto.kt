package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class PrintingDto(
    val numberOfInks: String,
    val copperplateWidth: Int,
    val copperplateLength: Int,
    val dueDate: LocalDate,
    val printingType: String,
    val supervisionDatetime: LocalDateTime,
    val printingDirection: Int,
    val copperplateName: String,
    val isMatte: Boolean,
)
