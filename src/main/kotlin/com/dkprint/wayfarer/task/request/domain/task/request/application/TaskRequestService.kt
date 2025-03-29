package com.dkprint.wayfarer.task.request.domain.task.request.application

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.ReadAllRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.SaveRequest
import com.dkprint.wayfarer.task.request.domain.task.request.dao.TaskRequestRepository
import com.dkprint.wayfarer.task.request.domain.task.request.domain.TaskRequest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TaskRequestService(
    private val taskRequestRepository: TaskRequestRepository,
    // private val searchService: SearchService,
    // private val codeSdk: CodeSdk,
) {
    companion object {
        private const val PAGE_SIZE: Int = 20
    }

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

    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime, pageable: Pageable): Page<TaskRequest> {
        return taskRequestRepository.findByCreatedAtBetweenOrderByIdAsc(start, end, pageable)
    }

    fun findByIdIn(taskRequestIds: List<Long>, pageable: Pageable): Page<TaskRequest> {
        return taskRequestRepository.findByIdInOrderByIdAsc(taskRequestIds, pageable)
    }

    fun findByCodeId(codeId: Long, pageable: Pageable): Page<TaskRequest> {
        return taskRequestRepository.findByCodeIdOrderByIdAsc(codeId, pageable)
    }

    fun findByTaskRequestNumber(keyword: String, pageable: Pageable): Page<TaskRequest> {
        return taskRequestRepository.findByTaskRequestNumberOrderByIdAsc(keyword, pageable)
    }
}
