package com.dkprint.wayfarer.task.request.printing.dto

import com.dkprint.wayfarer.task.request.printing.domain.Printing
import java.time.LocalDate
import java.time.LocalDateTime

data class PrintingDto(
    val numberOfInks: String,
    val copperplateWidth: Int,
    val copperplateLength: Int,
    val dueDate: LocalDate,
    val printingType: String,
    val supervisionDatetime: LocalDateTime,
    val printingDirection: Int,
    val copperplateName: String,
    val isMatte: Boolean,
) {
    companion object {
        fun of(printing: Printing, copperplateName: String): PrintingDto {
            return PrintingDto(
                numberOfInks = printing.numberOfInks,
                copperplateWidth = printing.copperplateWidth,
                copperplateLength = printing.copperplateLength,
                dueDate = printing.dueDate,
                printingType = printing.printingType,
                supervisionDatetime = printing.supervisionDatetime,
                printingDirection = printing.printingDirection,
                copperplateName = copperplateName,
                isMatte = printing.isMatte,
            )
        }
    }
}
