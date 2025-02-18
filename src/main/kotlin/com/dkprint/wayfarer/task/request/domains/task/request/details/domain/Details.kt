package com.dkprint.wayfarer.task.request.domains.task.request.details.domain

import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_details")
class Details(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    private var taskRequest: TaskRequest,

    @Column(name = "product_name")
    private var productName: String,

    @Column(name = "product_type")
    private var productType: String,

    @Column(name = "standard")
    private var standard: Int,

    @Column(name = "width")
    private var width: Int,

    @Column(name = "thickness")
    private var thickness: Int,

    @Column(name = "order_date")
    private val orderDate: LocalDate,

    @Column(name = "due_date")
    private var dueDate: LocalDate,

    @Column(name = "sheets_count")
    private var sheetsCount: Int,

    @Column(name = "meter")
    private var meter: Int,

    @Column(name = "rolls_count")
    private var rollsCount: Int
) {
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
