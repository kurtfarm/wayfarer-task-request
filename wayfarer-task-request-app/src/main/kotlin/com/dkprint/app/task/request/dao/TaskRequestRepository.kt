package com.dkprint.app.task.request.dao

import com.dkprint.app.task.request.domain.TaskRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRequestRepository : JpaRepository<TaskRequest, Long> {
    fun findByTaskRequestNumber(taskRequestNumber: String): TaskRequest?
    fun findByCodeId(codeId: Long): List<TaskRequest>
    fun findByIsDeletedIsFalseOrderByCreatedAtDesc(pageable: Pageable): Page<TaskRequest>
    fun findByIdInOrderByCreatedAtDesc(taskRequestIds: List<Long>, pageable: Pageable): Page<TaskRequest>
}
