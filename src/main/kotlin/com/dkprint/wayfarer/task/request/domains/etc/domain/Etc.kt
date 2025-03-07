package com.dkprint.wayfarer.task.request.domains.etc.domain

import com.dkprint.wayfarer.task.request.domains.etc.dto.EtcDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_etc")
class Etc(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0L,

    @Column(name = "task_request_id")
    var taskRequestId: Long,

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
    fun toDto(vendorName: String): EtcDto {
        return EtcDto(
            taskName = taskName,
            taskType = taskType,
            quantity = quantity,
            dueDate = dueDate,
            vendorName = vendorName,
            etcType = etcType,
        )
    }

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
