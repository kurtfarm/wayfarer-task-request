package com.dkprint.wayfarer.task.request.domains.details.domain

import com.dkprint.wayfarer.task.request.domains.details.dto.DetailsDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_details")
class Details(
    @Id
    @Column(name = "task_request_id")
    val taskRequestId: Long,

    @Column(name = "product_name")
    var productName: String,

    @Column(name = "product_type")
    var productType: String,

    @Column(name = "standard_width")
    var standardWidth: Int,

    @Column(name = "standard_length")
    var standardLength: Int,

    @Column(name = "standard_thickness")
    var standardThickness: Int,

    @Column(name = "quantity")
    var expectedQuantity: Int,

    @Column(name = "meter")
    var expectedMeter: Int,

    @Column(name = "expected_rolls_count")
    var expectedRollsCount: Int,

    @Column(name = "vendor_id")

    var vendorId: Long,

    @Column(name = "order_date")
    var orderDate: LocalDate = LocalDate.now(),

    @Column(name = "due_date")
    var dueDate: LocalDate,
) {
    fun toDto(vendorName: String): DetailsDto {
        return DetailsDto(
            productName = productName,
            productType = productType,
            standardWidth = standardWidth,
            standardLength = standardLength,
            standardThickness = standardThickness,
            expectedQuantity = expectedQuantity,
            expectedMeter = expectedMeter,
            expectedRollsCount = expectedRollsCount,
            vendorName = vendorName,
            orderDate = orderDate,
            dueDate = dueDate,
        )
    }

    companion object {
        fun of(taskRequestId: Long, detailsDto: DetailsDto): Details {
            return Details(
                taskRequestId = taskRequestId,
                orderDate = detailsDto.orderDate,
                productName = detailsDto.productName,
                productType = detailsDto.productType,
                dueDate = detailsDto.dueDate,
                standardWidth = detailsDto.standardWidth,
                standardLength = detailsDto.standardLength,
                standardThickness = detailsDto.standardThickness,
                expectedQuantity = detailsDto.expectedQuantity,
                expectedMeter = detailsDto.expectedMeter,
                expectedRollsCount = detailsDto.expectedRollsCount,
                vendorId = 0L,
            )
        }
    }
}
