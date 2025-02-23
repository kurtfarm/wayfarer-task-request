package com.dkprint.wayfarer.task.request.domains.printing.domain

import com.dkprint.wayfarer.task.request.domains.printing.dto.PrintingDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "task_request_printing")
class Printing(
    @Id
    @Column(name = "task_request_id")
    var taskRequestId: Long,

    @Column(name = "number_of_inks")
    var numberOfInks: String,

    @Column(name = "copperplate_width")
    var copperplateWidth: Int,

    @Column(name = "copperplate_length")
    var copperplateLength: Int,

    @Column(name = "due_date")
    var dueDate: LocalDate,

    @Column(name = "printing_type")
    var printingType: String,

    @Column(name = "supervision_datetime")
    var supervisionDatetime: LocalDateTime,

    @Column(name = "printing_direction")
    var printingDirection: Int,

    @Column(name = "copperplate_id")
    var copperplateId: Long,

    @Column(name = "is_matte")
    var isMatte: Boolean,
) {
    companion object {
        fun of(
            taskRequestId: Long,
            copperplateId: Long,
            printingDto: PrintingDto,
        ): Printing {
            return Printing(
                taskRequestId = taskRequestId,
                numberOfInks = printingDto.numberOfInks,
                copperplateWidth = printingDto.copperplateWidth,
                copperplateLength = printingDto.copperplateLength,
                dueDate = printingDto.dueDate,
                printingType = printingDto.printingType,
                supervisionDatetime = printingDto.supervisionDatetime,
                printingDirection = printingDto.printingDirection,
                copperplateId = copperplateId,
                isMatte = printingDto.isMatte,
            )
        }
    }
}
