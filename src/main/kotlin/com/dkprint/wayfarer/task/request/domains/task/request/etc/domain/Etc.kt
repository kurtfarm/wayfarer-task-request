package com.dkprint.wayfarer.task.request.domains.task.request.etc.domain

import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_etc")
class Etc(
    @Id
    @OneToOne
    @JoinColumn(name = "task_request_id")
    var taskRequest: TaskRequest,

    @Column(name = "task_name")
    private var taskName: String,

    @Column(name = "task_type")
    private var taskType: String,

    @Column(name = "quantity")
    private var quantity: Int,

    @Column(name = "due_date")
    private var dueDate: LocalDate,

    @Column(name = "vendor_id")
    private var vendorId: Long,

    @Column(name = "etc_type")
    private var etcType: Boolean
) {
    init {
        validate()
    }

    private fun validate() {
        require(taskName.isNotBlank()) { "작업명은 비어 있을 수 없습니다." }
        require(taskType.isNotBlank()) { "작업 구분은 비어 있을 수 없습니다." }
        require(quantity > 0) { "수량은 0보다 커야 합니다." }
        require(dueDate.isAfter(LocalDate.now())) { "납기일은 오늘 이후여야 합니다." }
    }
}
