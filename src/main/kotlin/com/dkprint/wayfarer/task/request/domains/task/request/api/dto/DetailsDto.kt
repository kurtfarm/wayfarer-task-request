package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDate

class DetailsDto(
    val orderDate: LocalDate,
    val productName: String,
    val productType: String,
    val dueDate: LocalDate,
    val standard: Int,
    val width: Int,
    val thickness: Int,
    val sheetsCount: Int,
    val meter: Int,
    val rollsCount: Int,
    val vendorName: String
)
