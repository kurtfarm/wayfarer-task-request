package com.dkprint.wayfarer.task.request.domains.processing.domain

import com.dkprint.wayfarer.task.request.domains.processing.dto.ProcessingDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "task_request_processing")
class Processing(
    @Id
    @Column(name = "task_request_id")
    var taskRequestId: Long,

    @Column(name = "task_type")
    var taskType: String,

    @Column(name = "quantity")
    var quantity: Int,

    @Column(name = "side")
    var side: String,

    @Column(name = "due_date")
    var dueDate: LocalDate,

    @Column(name = "vendor_id")
    var vendorId: Long = 0L,

    @Column(name = "perforation")
    var perforation: String,

    @Column(name = "upper_part")
    var upperPart: String,

    @Column(name = "notch")
    var notch: String,

    @Column(name = "plain_box")
    var plainBox: String,

    @Column(name = "round")
    var round: String,

    @Column(name = "zipper")
    var zipper: String,

    @Column(name = "stand")
    var stand: String,

    @Column(name = "opening_direction")
    var openingDirection: String,
) {
    companion object {
        fun of(
            taskRequestId: Long,
            vendorId: Long,
            processingDto: ProcessingDto,
        ): Processing {
            return Processing(
                taskRequestId = taskRequestId,
                taskType = processingDto.taskType,
                quantity = processingDto.quantity,
                side = processingDto.side,
                dueDate = processingDto.dueDate,
                vendorId = vendorId,
                perforation = processingDto.perforation,
                upperPart = processingDto.upperPart,
                notch = processingDto.notch,
                plainBox = processingDto.plainBox,
                round = processingDto.round,
                zipper = processingDto.zipper,
                stand = processingDto.stand,
                openingDirection = processingDto.openingDirection,
            )
        }
    }
}
