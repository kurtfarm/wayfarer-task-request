package com.dkprint.wayfarer.task.request.domains.task.request.application

import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestDto
import com.dkprint.wayfarer.task.request.domains.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import org.springframework.stereotype.Service

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,
) {
    fun create(taskRequestDto: TaskRequestDto): Long {
        val taskRequest: TaskRequest = TaskRequest.from(taskRequestDto)
        val savedTaskRequest: TaskRequest = taskRequestRepository.save(taskRequest)
        return savedTaskRequest.id
    }

    fun update(taskRequestNumber: Long, taskRequestDto: TaskRequestDto): Long {
        val oldTaskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        val newTaskRequest: TaskRequest = TaskRequest.from(taskRequestDto)
        oldTaskRequest.update(newTaskRequest)
        return oldTaskRequest.id
    }

    fun delete(taskRequestNumber: Long): Long {
        val taskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        taskRequest.isDeleted = true
        taskRequestRepository.deleteById(taskRequest.id)
        return taskRequest.id
    }
}
