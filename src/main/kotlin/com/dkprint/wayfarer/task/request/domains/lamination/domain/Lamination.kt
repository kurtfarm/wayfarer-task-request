package com.dkprint.wayfarer.task.request.domains.lamination.domain

import com.dkprint.wayfarer.task.request.domains.lamination.dto.LaminationDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_lamination")
class Lamination(
    @Id
    @Column(name = "task_request_id")
    val taskRequestId: Long,

    @Column(name = "sequence")
    var sequence: Int,

    @Column(name = "task_name")
    var taskName: String,

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
        ): Lamination {
            return Lamination(
                taskRequestId = taskRequestId,
                sequence = laminationDto.sequence,
                taskName = laminationDto.taskName,
                taskType = laminationDto.taskType,
                quantity = laminationDto.quantity,
                comment = laminationDto.comment,
                dueDate = laminationDto.dueDate,
            )
        }
    }
}
