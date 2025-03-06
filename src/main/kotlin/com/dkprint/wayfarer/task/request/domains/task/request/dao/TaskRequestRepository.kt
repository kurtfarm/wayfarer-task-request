package com.dkprint.wayfarer.task.request.domains.task.request.dao

import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRequestRepository : JpaRepository<TaskRequest, Long> {
    fun findByTaskRequestNumber(taskRequestNumber: String): TaskRequest?
}
