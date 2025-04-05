package com.dkprint.app.core.dto.request

import java.time.LocalDate

data class SearchRequest(
    val page: Int = 0,

    val searchType: String? = null,
    val keyword: String? = null,

    val dateType: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
)
