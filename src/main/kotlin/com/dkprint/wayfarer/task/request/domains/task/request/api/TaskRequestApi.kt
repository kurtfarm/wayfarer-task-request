package com.dkprint.wayfarer.task.request.domains.task.request.api

import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestDto
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestResponse
import com.dkprint.wayfarer.task.request.domains.task.request.application.TaskRequestFacade
import jakarta.validation.constraints.Size
import java.net.URI
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
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
    fun createTaskRequest(
        @RequestPart taskRequestDto: TaskRequestDto,
        @RequestPart @Size(max = 5) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<TaskRequestResponse> {
        taskRequestDto.printDesigns = printDesigns
        val taskRequestResponse: TaskRequestResponse = taskRequestFacade.create(taskRequestDto)
        return ResponseEntity.created(URI.create("/api/task-request/${taskRequestResponse.id}"))
            .body(taskRequestResponse)
    }

    @PatchMapping("/task-request/{taskRequestNumber}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateTaskRequest(
        @PathVariable taskRequestNumber: Long,
        @RequestPart taskRequestDto: TaskRequestDto,
        @RequestPart @Size(max = 5) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<TaskRequestResponse> {
        taskRequestDto.printDesigns = printDesigns
        val taskRequestResponse: TaskRequestResponse = taskRequestFacade.update(taskRequestNumber, taskRequestDto)
        return ResponseEntity.ok(taskRequestResponse)
    }

    @DeleteMapping("/task-request/{taskRequestNumber}")
    fun deleteTaskRequest(
        @PathVariable taskRequestNumber: Long,
    ): ResponseEntity<Void> {
        taskRequestFacade.delete(taskRequestNumber)
        return ResponseEntity.noContent().build()
    }
}
