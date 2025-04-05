package com.dkprint.wayfarer.task.request.processing.dto

import com.dkprint.wayfarer.task.request.processing.domain.Processing
import java.time.LocalDate

data class ProcessingDto(
    val taskType: String,
    val quantity: Int,
    val side: String,
    val dueDate: LocalDate,
    val vendorName: String,
    val perforation: String,
    val upperPart: String,
    val notch: String,
    val plainBox: String,
    val round: String,
    val zipper: String,
    val stand: String,
    val openingDirection: String,
) {
    companion object {
        fun of(processing: Processing, vendorName: String): ProcessingDto {
            return ProcessingDto(
                taskType = processing.taskType,
                quantity = processing.quantity,
                side = processing.side,
                dueDate = processing.dueDate,
                vendorName = vendorName,
                perforation = processing.perforation,
                upperPart = processing.upperPart,
                notch = processing.notch,
                plainBox = processing.plainBox,
                round = processing.round,
                zipper = processing.zipper,
                stand = processing.stand,
                openingDirection = processing.openingDirection,
            )
        }
    }
}
