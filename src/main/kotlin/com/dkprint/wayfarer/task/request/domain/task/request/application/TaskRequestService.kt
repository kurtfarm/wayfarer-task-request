package com.dkprint.wayfarer.task.request.domain.task.request.application

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.ReadAllRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.SaveRequest
import com.dkprint.wayfarer.task.request.domain.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.stereotype.Service

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,
    // private val searchService: SearchService,
    // private val codeSdk: CodeSdk,
) {
    fun create(saveRequest: SaveRequest): TaskRequest {
        val taskRequest: TaskRequest = TaskRequest.from(saveRequest)
        taskRequest.codeId = 1L // codeSdk.generate()
        val savedTaskRequest: TaskRequest = taskRequestRepository.save(taskRequest)
        val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val order: String = String.format("%02d", savedTaskRequest.id)
        taskRequest.taskRequestNumber = date + order
        return savedTaskRequest
    }

    fun read(taskRequestNumber: String): TaskRequest {
        val taskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        return taskRequest
    }

    fun readAll(readAllRequest: ReadAllRequest): List<TaskRequest> {
        return taskRequestRepository.findTop20ByIdGreaterThanOrderByIdAsc(readAllRequest.lastId)
    }

    fun update(taskRequestNumber: String, saveRequest: SaveRequest): TaskRequest {
        val oldTaskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        val newTaskRequest: TaskRequest = TaskRequest.from(saveRequest)
        oldTaskRequest.update(newTaskRequest)
        return oldTaskRequest
    }

    fun delete(taskRequestNumber: String): Long {
        val taskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        taskRequest.isDeleted = true
        taskRequestRepository.deleteById(taskRequest.id)
        return taskRequest.id
    }

    fun findByIdIn(lastId: Long, taskRequestIds: List<Long>): List<TaskRequest> {
        return taskRequestRepository.findTop20ByIdGreaterThanAndIdInOrderByIdAsc(lastId, taskRequestIds)
    }

    fun findByCodeId(lastId: Long, codeId: Long): List<TaskRequest> {
        return taskRequestRepository.findTop20ByIdGreaterThanAndCodeIdOrderByIdAsc(lastId, codeId)
    }

    fun findByTaskRequestNumber(lastId: Long, keyword: String): List<TaskRequest> {
        return taskRequestRepository.findTop20ByIdGreaterThanAndTaskRequestNumberOrderByIdAsc(lastId, keyword)
    }
}
