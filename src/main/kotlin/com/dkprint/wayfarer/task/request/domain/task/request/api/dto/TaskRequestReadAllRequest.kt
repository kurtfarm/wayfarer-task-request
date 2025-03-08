package com.dkprint.wayfarer.task.request.domain.task.request.api.dto

import java.time.LocalDate

data class TaskRequestReadAllRequest(
    val page: Int,
    val searchType: String,
    val keyword: String,
    val dateType: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
