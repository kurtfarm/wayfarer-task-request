package com.dkprint.app.task.request.domain

enum class DateType(val description: String) {
    ORDER("발주일"),
    FABRIC("원단 입고예정일"),
    COPPERPLATE("동판 입고예정일"),
}
