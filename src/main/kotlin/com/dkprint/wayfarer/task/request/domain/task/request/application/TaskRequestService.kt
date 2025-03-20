package com.dkprint.wayfarer.task.request.domain.task.request.application

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSaveRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSearchRequest
import com.dkprint.wayfarer.task.request.domain.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,
    // private val codeSdk: CodeSdk,
) {
    fun create(taskRequestSaveRequest: TaskRequestSaveRequest): TaskRequest {
        val taskRequest: TaskRequest = TaskRequest.from(taskRequestSaveRequest)
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

    fun update(taskRequestNumber: String, taskRequestSaveRequest: TaskRequestSaveRequest): TaskRequest {
        val oldTaskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        val newTaskRequest: TaskRequest = TaskRequest.from(taskRequestSaveRequest)
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

    fun search(taskRequestSearchRequest: TaskRequestSearchRequest): Page<TaskRequest> {
        return taskRequestRepository.search(taskRequestSearchRequest)
    }
}
