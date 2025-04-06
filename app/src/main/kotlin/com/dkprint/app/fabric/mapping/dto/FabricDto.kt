package com.dkprint.app.fabric.mapping.dto

import com.dkprint.app.core.annotation.NoArg
import java.time.LocalDate

@NoArg
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
