package com.dkprint.wayfarer.task.request.domain.task.request.details.domain

import com.dkprint.wayfarer.task.request.domain.model.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_details")
class TaskRequestDetails(
    @Column(name = "product_name")
    private val productName: String,

    @Column(name = "product_type")
    private val productType: String,

    @Column(name = "standard")
    private val standard: Int,

    @Column(name = "width")
    private val width: Int,

    @Column(name = "thickness")
    private val thickness: Int,

    @Column(name = "order_date")
    private val orderDate: LocalDate,

    @Column(name = "due_date")
    private val dueDate: LocalDate,

    @Column(name = "sheets_count")
    private val sheetsCount: Int,

    @Column(name = "meter")
    private val meter: Int,

    @Column(name = "rolls_count")
    private val rollsCount: Int
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long = 0L

    init {
        validate()
    }

    private fun validate() {
        require(width > 0) {
            "너비는 0보다 커야 합니다."
        }
        require(thickness > 0) {
            "두께는 0보다 커야 합니다."
        }
        require(sheetsCount > 0) {
            "예상 수량은 0보다 많아야 합니다."
        }
        require(meter > 0) {
            "예상 길이는 0보다 커야 합니다."
        }
        require(rollsCount > 0) {
            "롤 수는 0보다 많아야 합니다."
        }
        require(orderDate.isBefore(dueDate)) {
            "발주일은 납기일 이전이어야 합니다."
        }
    }
}
