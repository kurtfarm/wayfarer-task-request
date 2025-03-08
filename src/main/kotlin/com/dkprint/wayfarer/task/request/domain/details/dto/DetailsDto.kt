package com.dkprint.wayfarer.task.request.domain.details.dto

import java.time.LocalDate

data class DetailsDto(
    val productName: String,
    val productType: String,
    val standardWidth: Int,
    val standardLength: Int,
    val standardThickness: Int,
    val expectedQuantity: Int,
    val expectedMeter: Int,
    val expectedRollsCount: Int,
    val vendorName: String,
    val orderDate: LocalDate,
    val dueDate: LocalDate,
)
