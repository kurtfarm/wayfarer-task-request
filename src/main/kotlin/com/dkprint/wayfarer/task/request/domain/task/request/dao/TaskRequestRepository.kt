package com.dkprint.wayfarer.task.request.domain.task.request.dao

import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import java.time.LocalDateTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRequestRepository : JpaRepository<TaskRequest, Long> {
    fun findByTaskRequestNumber(taskRequestNumber: String): TaskRequest?
    fun findByTaskRequestNumberOrderByIdAsc(taskRequestNumber: String, pageable: Pageable): Page<TaskRequest>
    fun findAllByOrderByIdAsc(pageable: Pageable): Page<TaskRequest>
    fun findByCodeIdOrderByIdAsc(codeId: Long, pageable: Pageable): Page<TaskRequest>
    fun findByCreatedAtBetweenOrderByIdAsc(startDate: LocalDateTime, endDate: LocalDateTime, pageable: Pageable): Page<TaskRequest>
    fun findByIdInOrderByIdAsc(ids: List<Long>, pageable: Pageable): Page<TaskRequest>
}
