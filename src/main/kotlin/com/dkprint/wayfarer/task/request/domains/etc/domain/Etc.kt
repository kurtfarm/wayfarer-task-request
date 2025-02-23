package com.dkprint.wayfarer.task.request.domains.etc.domain

import com.dkprint.wayfarer.task.request.domains.etc.dto.EtcDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_etc")
class Etc(
    @Id
    @Column(name = "task_request_id")
    val taskRequestId: Long,

    @Column(name = "task_name")
    var taskName: String,

    @Column(name = "task_type")
    var taskType: String,

    @Column(name = "quantity")
    var quantity: Int,

    @Column(name = "due_date")
    var dueDate: LocalDate,

    @Column(name = "vendor_id")
    var vendorId: Long = 0L,

    @Column(name = "etc_type")
    var etcType: Boolean,
) {
    companion object {
        fun of(
            taskRequestId: Long,
            vendorId: Long,
            etcDto: EtcDto,
        ): Etc {
            return Etc(
                taskRequestId = taskRequestId,
                taskName = etcDto.taskName,
                taskType = etcDto.taskType,
                quantity = etcDto.quantity,
                dueDate = etcDto.dueDate,
                vendorId = vendorId,
                etcType = etcDto.etcType,
            )
        }
    }
}
