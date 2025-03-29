package com.dkprint.wayfarer.task.request.domain.task.request.api

import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.ReadAllRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.SaveRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.request.SearchRequest
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.response.ReadAllResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.response.ReadResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.response.SaveResponse
import com.dkprint.wayfarer.task.request.domain.task.request.api.dto.response.SearchResponse
import com.dkprint.wayfarer.task.request.domain.task.request.application.TaskRequestFacade
import jakarta.validation.constraints.Size
import java.net.URI
import org.springframework.data.domain.Page
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
    private val taskRequestFacade: TaskRequestFacade,
) {
    @PostMapping("\${task-request.create}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart saveRequest: SaveRequest,
        @RequestPart @Size(max = 5) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<SaveResponse> {
        saveRequest.printDesigns = printDesigns
        val saveResponse: SaveResponse = taskRequestFacade.create(saveRequest)
        return ResponseEntity.created(URI.create("/api/task-request/${saveResponse.taskRequestNumber}"))
            .body(saveResponse)
    }

    @GetMapping("\${task-request.read}")
    fun read(@PathVariable taskRequestNumber: String): ResponseEntity<ReadResponse> {
        val readResponse: ReadResponse = taskRequestFacade.read(taskRequestNumber)
        return ResponseEntity.ok(readResponse)
    }

    @GetMapping("\${task-request.read-all}")
    fun readAll(readAllRequest: ReadAllRequest): ResponseEntity<Page<ReadAllResponse>> {
        val readAllResponse: Page<ReadAllResponse> = taskRequestFacade.readAll(readAllRequest)
        return ResponseEntity.ok(readAllResponse)
    }

    @PatchMapping("\${task-request.update}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(
        @PathVariable taskRequestNumber: String,
        @RequestPart saveRequest: SaveRequest,
        @RequestPart @Size(max = 5) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<SaveResponse> {
        saveRequest.printDesigns = printDesigns
        val saveResponse: SaveResponse = taskRequestFacade.update(taskRequestNumber, saveRequest)
        return ResponseEntity.ok(saveResponse)
    }

    @DeleteMapping("\${task-request.delete}")
    fun delete(@PathVariable taskRequestNumber: String): ResponseEntity<Void> {
        taskRequestFacade.delete(taskRequestNumber)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("\${task-request.search}")
    fun search(@ModelAttribute searchRequest: SearchRequest): ResponseEntity<Page<SearchResponse>> {
        val searchResponse: Page<SearchResponse> = taskRequestFacade.search(searchRequest)
        return ResponseEntity.ok(searchResponse)
    }
}
