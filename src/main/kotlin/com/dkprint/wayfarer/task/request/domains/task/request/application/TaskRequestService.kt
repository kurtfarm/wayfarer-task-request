package com.dkprint.wayfarer.task.request.domains.task.request.application

import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestSaveRequest
import com.dkprint.wayfarer.task.request.domains.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,
    // private val codeSdk: CodeSdk,
) {
    fun create(taskRequestSaveRequest: TaskRequestSaveRequest): Long {
        val taskRequest: TaskRequest = TaskRequest.from(taskRequestSaveRequest)
        taskRequest.codeId = 1L // codeSdk.generate()
        val savedTaskRequest: TaskRequest = taskRequestRepository.save(taskRequest)
        val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val order: String = String.format("%02d", savedTaskRequest.id)
        taskRequest.taskRequestNumber = date + order
        return savedTaskRequest.id
    }

    fun update(taskRequestNumber: String, taskRequestSaveRequest: TaskRequestSaveRequest): Long {
        val oldTaskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        val newTaskRequest: TaskRequest = TaskRequest.from(taskRequestSaveRequest)
        oldTaskRequest.update(newTaskRequest)
        return oldTaskRequest.id
    }

    fun delete(taskRequestNumber: String): Long {
        val taskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        taskRequest.isDeleted = true
        taskRequestRepository.deleteById(taskRequest.id)
        return taskRequest.id
    }

    fun readAll(pageable: Pageable): Page<TaskRequest> {
        return taskRequestRepository.findAll(pageable)
    }
}
