package com.dkprint.wayfarer.task.request.domain.task.request.application

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.ReadAllRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.SaveRequest
import com.dkprint.wayfarer.task.request.domain.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,
    // TODO: private val codeSdk: CodeSdk,
) {
    companion object {
        private const val PAGE_SIZE = 20
    }

    fun create(saveRequest: SaveRequest): TaskRequest {
        val taskRequest: TaskRequest = TaskRequest.from(saveRequest)
        taskRequest.codeId = 1L // TODO: codeSdk.generate()
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

    fun readAll(readAllRequest: ReadAllRequest): Page<TaskRequest> {
        val pageable: Pageable = PageRequest.of(readAllRequest.page, PAGE_SIZE)
        return taskRequestRepository.findAllByOrderByIdAsc(pageable)
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

    fun findByIdIn(taskRequestIds: List<Long>, pageable: Pageable): Page<TaskRequest> {
        return taskRequestRepository.findByIdIn(taskRequestIds, pageable)
    }

    fun findByCodeId(codeId: Long): List<TaskRequest> {
        return taskRequestRepository.findByCodeId(codeId)
    }

    fun findByTaskRequestNumber(keyword: String): List<TaskRequest> {
        return listOf(taskRequestRepository.findByTaskRequestNumber(keyword)!!)
    }
}
