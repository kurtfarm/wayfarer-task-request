package com.dkprint.wayfarer.task.request.domain.task.request.dao

import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRequestRepository : JpaRepository<TaskRequest, Long> {
    fun findByTaskRequestNumber(taskRequestNumber: String): TaskRequest?
    fun findAllByTaskRequestNumber(taskRequestNumber: String, pageable: Pageable): Page<TaskRequest>
    fun findByCodeId(codeId: Long, pageable: Pageable): Page<TaskRequest>
    fun findAllByOrderByIdAsc(pageable: Pageable): Page<TaskRequest>
    fun findByIdIn(taskRequestIds: List<Long>, pageable: Pageable): Page<TaskRequest>
}
