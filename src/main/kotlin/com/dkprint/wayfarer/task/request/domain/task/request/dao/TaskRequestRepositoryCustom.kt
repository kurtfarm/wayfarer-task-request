package com.dkprint.wayfarer.task.request.domain.task.request.dao

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSearchRequest
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
interface TaskRequestRepositoryCustom {
    fun search(taskRequestSearchRequest: TaskRequestSearchRequest, pageable: Pageable): Page<TaskRequest>
}
