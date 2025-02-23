package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDate

data class FabricDto(
    val fabricClass: Int,
    val fabricType: String,
    val standardThickness: Int,
    val standardWidth: Int,
    val standardLength: Int,
    val quantity: Int,
    val vendorName: String,
    val expectedArrivalDate: LocalDate,
)
