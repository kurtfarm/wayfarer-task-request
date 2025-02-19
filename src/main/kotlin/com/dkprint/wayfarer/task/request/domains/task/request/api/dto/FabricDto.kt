package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDate

class FabricDto(
    val fabricClass: String,
    val fabricType: String,
    val thickness: Int,
    val standard: Int,
    val width: Int,
    val quantity: Int,
    val vendorName: String,
    val eta: LocalDate
)
