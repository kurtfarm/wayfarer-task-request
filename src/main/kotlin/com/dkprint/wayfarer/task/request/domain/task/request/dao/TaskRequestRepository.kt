package com.dkprint.wayfarer.task.request.domain.task.request.dao

import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import java.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRequestRepository : JpaRepository<TaskRequest, Long> {
    fun findByTaskRequestNumber(taskRequestNumber: String): TaskRequest?
    fun findTop20ByIdGreaterThanAndTaskRequestNumberOrderByIdAsc(
        lastId: Long,
        taskRequestNumber: String,
    ): List<TaskRequest>

    fun findTop20ByIdGreaterThanAndCodeIdOrderByIdAsc(
        lastId: Long,
        codeId: Long,
    ): List<TaskRequest>

    fun findTop20ByIdGreaterThanAndCreatedAtBetweenOrderByIdAsc(
        lastId: Long,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<TaskRequest>

    fun findTop20ByIdGreaterThanAndIdInOrderByIdAsc(
        lastId: Long,
        ids: List<Long>,
    ): List<TaskRequest>

    fun findTop20ByIdGreaterThanOrderByIdAsc(lastId: Long): List<TaskRequest>
}
