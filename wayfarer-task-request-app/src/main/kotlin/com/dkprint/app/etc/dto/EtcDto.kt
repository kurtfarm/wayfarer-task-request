package com.dkprint.app.etc.dto

import com.dkprint.app.etc.domain.Etc
import java.time.LocalDate

class EtcDto(
    val taskName: String,
    val taskType: String,
    val quantity: Int,
    val dueDate: LocalDate,
    val vendorName: String,
    val etcType: Boolean,
) {
    companion object {
        fun of(etc: Etc, vendorName: String): EtcDto {
            return EtcDto(
                taskName = etc.taskName,
                taskType = etc.taskType,
                quantity = etc.quantity,
                dueDate = etc.dueDate,
                vendorName = vendorName,
                etcType = etc.etcType,
            )
        }
    }
}
