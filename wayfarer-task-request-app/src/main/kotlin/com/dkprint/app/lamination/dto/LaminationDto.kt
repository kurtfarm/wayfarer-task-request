package com.dkprint.app.lamination.dto

import com.dkprint.app.lamination.domain.Lamination
import java.time.LocalDate

data class LaminationDto(
    val sequence: Int,
    val taskVendorName: String,
    val taskType: String,
    val quantity: Int,
    val comment: String,
    val dueDate: LocalDate,
) {
    companion object {
        fun of(lamination: Lamination, taskVendorName: String): LaminationDto {
            return LaminationDto(
                sequence = lamination.sequence,
                taskVendorName = taskVendorName,
                taskType = lamination.taskType,
                quantity = lamination.quantity,
                comment = lamination.comment,
                dueDate = lamination.dueDate,
            )
        }
    }
}
