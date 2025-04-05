package com.dkprint.app.task.request.application

import com.dkprint.app.core.dto.request.ReadAllRequest
import com.dkprint.app.core.dto.request.UpsertRequest
import com.dkprint.app.task.request.dao.TaskRequestRepository
import com.dkprint.app.task.request.domain.TaskRequest
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
        private const val PAGE_SIZE: Int = 20
    }

    fun create(request: UpsertRequest): TaskRequest {
        val taskRequest: TaskRequest = TaskRequest.from(request)
        taskRequest.codeId = 1L // TODO: codeSdk.generate()
        val saved: TaskRequest = taskRequestRepository.save(taskRequest)
        createTaskRequestNumber(saved)
        return saved
    }

    fun read(taskRequestNumber: String): TaskRequest {
        val taskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        return taskRequest
    }

    fun readAll(request: ReadAllRequest): Page<TaskRequest> {
        val pageable: Pageable = PageRequest.of(request.page, PAGE_SIZE)
        return taskRequestRepository.findByIsDeletedIsFalseOrderByCreatedAtDesc(pageable)
    }

    fun update(taskRequestNumber: String, request: UpsertRequest): TaskRequest {
        val existing: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        val updated: TaskRequest = TaskRequest.from(request)
        return existing.update(updated)
    }

    fun delete(taskRequestNumber: String): Long {
        val taskRequest: TaskRequest = taskRequestRepository.findByTaskRequestNumber(taskRequestNumber)
            ?: throw IllegalArgumentException("작업 의뢰서 번호: $taskRequestNumber 조회 오류")
        taskRequest.isDeleted = true
        return taskRequest.id
    }

    fun findByIdIn(taskRequestIds: List<Long>, pageable: Pageable): Page<TaskRequest> {
        return taskRequestRepository.findByIdInOrderByCreatedAtDesc(taskRequestIds, pageable)
    }

    fun findByCodeId(codeId: Long): List<TaskRequest> {
        return taskRequestRepository.findByCodeId(codeId)
    }

    fun findByTaskRequestNumber(keyword: String): List<TaskRequest> {
        return listOf(taskRequestRepository.findByTaskRequestNumber(keyword)!!)
    }

    private fun createTaskRequestNumber(taskRequest: TaskRequest) {
        val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val sequence: String = String.format("%02d", taskRequest.id)
        taskRequest.taskRequestNumber = date + sequence
    }
}
