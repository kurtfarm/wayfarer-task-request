package com.dkprint.app.core.dto.response

import java.time.LocalDate

data class ReadAllResponse(
    val taskRequestId: Long,
    val orderDate: LocalDate,
    val taskRequestNumber: String,
    val productCode: String,
    val productName: String,
    val productStandard: String,
    val fabricExpectedArrivalDate: LocalDate,
    val copperplateExpectedArrivalDate: LocalDate,
    val productVendorName: String,
    val laminationVendorNames: List<String>,
    val processingVendorName: String,
)
