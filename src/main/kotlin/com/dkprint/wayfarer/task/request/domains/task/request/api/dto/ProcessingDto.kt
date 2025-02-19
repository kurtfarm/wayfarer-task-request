package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDate

class ProcessingDto(
    val taskType: String,
    val quantity: Int,
    val side: String,
    val dueDate: LocalDate,
    val vendorName: Long,
    val perforation: String,
    val upperPart: String,
    val notch: String,
    val plainBox: String,
    val round: String,
    val zipper: String,
    val stand: String,
    val openingDirection: String
)
