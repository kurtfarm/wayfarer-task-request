package com.dkprint.wayfarer.task.request.domains.task.request.application

import com.dkprint.wayfarer.task.request.domains.common.application.MinioService
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestDto
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestResponse
import com.dkprint.wayfarer.task.request.domains.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domains.task.request.domain.TaskRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,
    private val taskRequestFacade: TaskRequestFacade,
    private val minioService: MinioService,
) {
    @Transactional
    fun create(
        taskRequestDto: TaskRequestDto,
    ): TaskRequestResponse {
        val taskRequest: TaskRequest = TaskRequest.from(taskRequestDto)
        val savedTaskRequest: TaskRequest = taskRequestRepository.save(taskRequest)
        taskRequestFacade.saveData(taskRequestDto, savedTaskRequest.id)
        return TaskRequestResponse(id = savedTaskRequest.id, status = true)
    }

    @Transactional
    fun update(
        taskRequestNumber: Long,
        taskRequestDto: TaskRequestDto,
    ): TaskRequestResponse {
        val oldTaskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        val newTaskRequest: TaskRequest = TaskRequest.from(taskRequestDto)
        oldTaskRequest.update(newTaskRequest)
        taskRequestFacade.saveData(taskRequestDto, oldTaskRequest.id)
        return TaskRequestResponse(id = oldTaskRequest.id, status = true)
    }

    @Transactional
    fun delete(taskRequestNumber: Long) {
        val taskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        taskRequest.isDeleted = true
        minioService.delete(taskRequest.id)
    }
}
