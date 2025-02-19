package com.dkprint.wayfarer.task.request.domains.task.request.api

import com.dkprint.wayfarer.task.request.domains.task.request.api.dto.TaskRequestDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TaskRequestApi {
    @PostMapping("/task-request")
    fun taskRequest(@RequestBody taskRequestDto: TaskRequestDto) {
        TODO()
    }
}
