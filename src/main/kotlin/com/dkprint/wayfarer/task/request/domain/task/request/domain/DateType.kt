package com.dkprint.wayfarer.task.request.domain.task.request.domain

enum class DateType(val description: String) {
    ORDER("발주일"),
    FABRIC("원단 입고예정일"),
    COPPERPLATE("동판 입고예정일")
}
