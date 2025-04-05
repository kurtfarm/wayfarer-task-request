package com.dkprint.wayfarer.task.request.core.path

object ApiPath {
    object TaskRequest {
        const val CREATE: String = "/api/task-request"
        const val READ: String = "/api/task-request/{taskRequestNumber}"
        const val READ_ALL: String = "/api/task-request"
        const val UPDATE: String = "/api/task-request/{taskRequestNumber}"
        const val DELETE: String = "/api/task-request/{taskRequestNumber}"
        const val SEARCH: String = "/api/task-request/search"
    }
}
