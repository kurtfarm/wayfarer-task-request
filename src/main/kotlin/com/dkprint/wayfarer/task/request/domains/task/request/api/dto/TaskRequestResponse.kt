package com.dkprint.wayfarer.task.request.domains.task.request.api.dto

import java.time.LocalDateTime

data class TaskRequestResponse(
    val id: Long,
    val status: Boolean,
    val time: LocalDateTime = LocalDateTime.now(),
)
