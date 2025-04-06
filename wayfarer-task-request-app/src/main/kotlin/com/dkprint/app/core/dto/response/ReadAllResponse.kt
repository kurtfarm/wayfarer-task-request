package com.dkprint.app.core.dto.response

data class ReadAllResponse(
    val taskRequestId: Long,
    val orderDate: String,
    val taskRequestNumber: String,
    val productCode: String,
    val productName: String,
    val productStandard: String,
    val fabricExpectedArrivalDate: String,
    val copperplateExpectedArrivalDate: String,
    val productVendorName: String,
    val laminationVendorNames: List<String>,
    val processingVendorName: String,
)
