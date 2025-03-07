package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDateTime

data class TaskRequestSaveResponse(
    val id: Long,
    val taskRequestNumber: String,
    val status: Boolean,
    val time: LocalDateTime = LocalDateTime.now(),
)
