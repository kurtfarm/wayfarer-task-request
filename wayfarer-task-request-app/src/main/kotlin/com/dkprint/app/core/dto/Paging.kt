package com.dkprint.app.core.dto

import org.springframework.data.domain.Page

data class Paging<T>(
    val content: List<T>,
    val totalPages: Int,
    val isLast: Boolean,
) {
    companion object {
        fun <T> from(page: Page<T>): Paging<T> {
            return Paging(
                content = page.content,
                totalPages = page.totalPages,
                isLast = page.isLast
            )
        }
    }
}

