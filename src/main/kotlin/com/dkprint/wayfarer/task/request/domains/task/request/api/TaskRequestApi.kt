package com.dkprint.wayfarer.task.request.domains.task.request.api

import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestDto
import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestResponse
import com.dkprint.wayfarer.task.request.domains.task.request.application.TaskRequestService
import java.net.URI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TaskRequestApi(
    val taskRequestService: TaskRequestService
) {
    @PostMapping("/task-request")
    fun taskRequest(@RequestBody taskRequestDto: TaskRequestDto): ResponseEntity<TaskRequestResponse> {
        val savedTaskRequest: TaskRequestResponse = taskRequestService.create(taskRequestDto)
        return ResponseEntity.created(URI.create("/api/task-request/" + savedTaskRequest.id))
            .body(savedTaskRequest)
    }
}
