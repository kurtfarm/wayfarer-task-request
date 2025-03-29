package com.dkprint.wayfarer.task.request.domain.task.request.api.dto.response

import java.time.LocalDateTime

data class SaveResponse(
    val id: Long,
    val taskRequestNumber: String,
    val status: Boolean,
    val time: LocalDateTime = LocalDateTime.now(),
)
