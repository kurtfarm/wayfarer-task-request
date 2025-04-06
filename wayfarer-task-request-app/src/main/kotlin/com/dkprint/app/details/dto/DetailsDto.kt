package com.dkprint.app.details.dto

import com.dkprint.app.core.annotation.NoArg
import com.dkprint.app.details.domain.Details
import java.time.LocalDate

@NoArg
data class DetailsDto(
    val productName: String,
    val productType: String,
    val standardWidth: Int,
    val standardLength: Int,
    val standardHeight: Int?,
    val expectedQuantity: Int,
    val expectedMeter: Int,
    val expectedRollsCount: Int,
    val vendorName: String,
    val orderDate: LocalDate,
    val dueDate: LocalDate,
) {
    companion object {
        fun of(details: Details, vendorName: String): DetailsDto {
            return DetailsDto(
                productName = details.productName,
                productType = details.productType,
                standardWidth = details.standardWidth,
                standardLength = details.standardLength,
                standardHeight = details.standardHeight,
                expectedQuantity = details.expectedQuantity,
                expectedMeter = details.expectedMeter,
                expectedRollsCount = details.expectedRollsCount,
                vendorName = vendorName,
                orderDate = details.orderDate,
                dueDate = details.dueDate,
            )
        }
    }
}
