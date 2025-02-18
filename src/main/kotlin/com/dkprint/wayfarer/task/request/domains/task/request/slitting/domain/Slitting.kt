package com.dkprint.wayfarer.task.request.domains.task.request.slitting.domain

import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_slitting")
class Slitting(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    private var taskRequest: TaskRequest,

    @Column(name = "vendor_id")
    private var vendorId: Long,

    @Column(name = "slitting_width")
    private var slittingWidth: Int,

    @Column(name = "slitting_height")
    private var slittingHeight: Int,

    @Column(name = "quantity")
    private var quantity: Long,

    @Column(name = "due_date")
    private var dueDate: LocalDate
) {
    init {
        validate()
    }

    private fun validate() {
        require(slittingWidth > 0) {
            "너비는 0보다 커야 합니다."
        }
        require(slittingHeight > 0) {
            "높이는 0보다 커야 합니다."
        }
        require(dueDate.isAfter(LocalDate.now())) {
            "납기일은 현재 날짜 이후여야 합니다."
        }
    }
}
