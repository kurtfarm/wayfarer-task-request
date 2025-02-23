package com.dkprint.wayfarer.task.request.domains.slitting.domain

import com.dkprint.wayfarer.task.request.domains.slitting.dto.SlittingDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_slitting")
class Slitting(
    @Id
    @Column(name = "task_request_id")
    var taskRequestId: Long,

    @Column(name = "slitting_width")
    var slittingWidth: Int,

    @Column(name = "slitting_length")
    var slittingLength: Int,

    @Column(name = "quantity")
    var quantity: Long,

    @Column(name = "due_date")
    var dueDate: LocalDate,

    @Column(name = "vendor_id")
    var vendorId: Long = 0L,
) {
    companion object {
        fun of(
            taskRequestId: Long,
            vendorId: Long,
            slittingDto: SlittingDto,
        ): Slitting {
            return Slitting(
                taskRequestId = taskRequestId,
                slittingWidth = slittingDto.slittingWidth,
                slittingLength = slittingDto.slittingLength,
                quantity = slittingDto.quantity,
                dueDate = slittingDto.dueDate,
                vendorId = vendorId,
            )
        }
    }
}
