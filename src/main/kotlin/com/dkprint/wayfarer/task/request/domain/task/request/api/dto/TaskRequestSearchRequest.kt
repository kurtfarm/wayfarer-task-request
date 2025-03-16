package com.dkprint.wayfarer.task.request.domain.task.request.api.dto

import java.time.LocalDate

data class TaskRequestSearchRequest(
    val page: Int = 0,
    val searchType: String? = null,
    val keyword: String? = null,
    val dateType: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
)
