package com.dkprint.wayfarer.task.request.domain.task.request.api

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestReadResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSaveRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSaveResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSearchRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSearchResponse
import com.dkprint.wayfarer.task.request.domain.task.request.application.TaskRequestFacade
import jakarta.validation.constraints.Size
import java.net.URI
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class TaskRequestApi(
    val taskRequestFacade: TaskRequestFacade,
) {
    @PostMapping("\${task-request.create}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart taskRequestSaveRequest: TaskRequestSaveRequest,
        @RequestPart @Size(max = 5) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<TaskRequestSaveResponse> {
        taskRequestSaveRequest.printDesigns = printDesigns
        val taskRequestSaveResponse: TaskRequestSaveResponse = taskRequestFacade.create(taskRequestSaveRequest)
        return ResponseEntity.created(URI.create("/api/task-request/${taskRequestSaveResponse.id}"))
            .body(taskRequestSaveResponse)
    }

    @PatchMapping("\${task-request.update}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(
        @PathVariable taskRequestNumber: String,
        @RequestPart taskRequestSaveRequest: TaskRequestSaveRequest,
        @RequestPart @Size(max = 5) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<TaskRequestSaveResponse> {
        taskRequestSaveRequest.printDesigns = printDesigns
        val taskRequestSaveResponse: TaskRequestSaveResponse =
            taskRequestFacade.update(taskRequestNumber, taskRequestSaveRequest)
        return ResponseEntity.ok(taskRequestSaveResponse)
    }

    @DeleteMapping("\${task-request.delete}")
    fun delete(@PathVariable taskRequestNumber: String): ResponseEntity<Void> {
        taskRequestFacade.delete(taskRequestNumber)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("\${task-request.search}")
    fun search(@ModelAttribute taskRequestSearchRequest: TaskRequestSearchRequest): Page<TaskRequestSearchResponse> {
        val sort: Sort = Sort.by("id").ascending()
        val pageable: Pageable = PageRequest.of(taskRequestSearchRequest.page, 20, sort)
        return taskRequestFacade.search(taskRequestSearchRequest, pageable)
    }

    @GetMapping("\${task-request.read}")
    fun read(@PathVariable taskRequestNumber: String): TaskRequestReadResponse {
        return taskRequestFacade.read(taskRequestNumber)
    }
}
