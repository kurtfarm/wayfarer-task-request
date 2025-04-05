package com.dkprint.app.lamination.domain

import com.dkprint.app.lamination.dto.LaminationDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_lamination")
class Lamination(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "task_request_id")
    val taskRequestId: Long,

    @Column(name = "sequence")
    var sequence: Int,

    @Column(name = "task_vendor_id")
    var vendorId: Long = 0L,

    @Column(name = "task_type")
    var taskType: String,

    @Column(name = "quantity")
    var quantity: Int,

    @Column(name = "comment")
    var comment: String,

    @Column(name = "due_date")
    var dueDate: LocalDate,
) {
    companion object {
        fun of(
            taskRequestId: Long,
            laminationDto: LaminationDto,
            vendorId: Long,
        ): Lamination {
            return Lamination(
                taskRequestId = taskRequestId,
                sequence = laminationDto.sequence,
                vendorId = vendorId,
                taskType = laminationDto.taskType,
                quantity = laminationDto.quantity,
                comment = laminationDto.comment,
                dueDate = laminationDto.dueDate,
            )
        }
    }
}
