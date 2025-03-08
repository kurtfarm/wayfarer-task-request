package com.dkprint.wayfarer.task.request.domain.task.request.api

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestReadAllRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestReadAllResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestReadResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSaveRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.TaskRequestSaveResponse
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class TaskRequestApi(
    val taskRequestFacade: TaskRequestFacade,
) {
    @PostMapping("/task-request", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart taskRequestSaveRequest: TaskRequestSaveRequest,
        @RequestPart @Size(max = 5) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<TaskRequestSaveResponse> {
        taskRequestSaveRequest.printDesigns = printDesigns
        val taskRequestSaveResponse: TaskRequestSaveResponse = taskRequestFacade.create(taskRequestSaveRequest)
        return ResponseEntity.created(URI.create("/api/task-request/${taskRequestSaveResponse.id}"))
            .body(taskRequestSaveResponse)
    }

    @PatchMapping("/task-request/{taskRequestNumber}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
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

    @DeleteMapping("/task-request/{taskRequestNumber}")
    fun delete(@PathVariable taskRequestNumber: String): ResponseEntity<Void> {
        taskRequestFacade.delete(taskRequestNumber)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/task-request")
    fun readAll(@ModelAttribute taskRequestReadAllRequest: TaskRequestReadAllRequest): Page<TaskRequestReadAllResponse> {
        val sort: Sort = Sort.by("id").ascending()
        val pageable: Pageable = PageRequest.of(taskRequestReadAllRequest.page, 20, sort)
        return taskRequestFacade.readAll(pageable)
    }

    @GetMapping("/task-request/{taskRequestNumber}")
    fun read(@PathVariable taskRequestNumber: String): TaskRequestReadResponse {
        return taskRequestFacade.read(taskRequestNumber)
    }
}
