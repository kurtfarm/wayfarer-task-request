package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDate

data class FabricDto(
    val fabricClass: String,
    val fabricType: String,
    val thickness: Int,
    val width: Int,
    val length: Int,
    val quantity: Int,
    val vendorName: String,
    val expectedArrivalDate: LocalDate,
)
