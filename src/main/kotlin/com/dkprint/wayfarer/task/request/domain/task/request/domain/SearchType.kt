package com.dkprint.wayfarer.task.request.domain.task.request.domain

enum class SearchType(val description: String) {
    PRODUCT_NAME("제품명"),
    PRODUCT_STANDARD("제품 규격"),
    PRODUCT_CODE("제품 코드"),
    TASK_REQUEST_NUMBER("작업의뢰서 번호"),
    VENDOR("거래처"),
}
