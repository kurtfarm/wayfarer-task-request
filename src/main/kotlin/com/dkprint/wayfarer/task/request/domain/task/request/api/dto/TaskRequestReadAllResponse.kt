package com.dkprint.wayfarer.task.request.domain.task.request.api.dto

import java.time.LocalDate

data class TaskRequestReadAllResponse(
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
