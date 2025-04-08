package com.dkprint.api.task.request

import com.dkprint.app.core.dto.Paging
import com.dkprint.app.core.dto.request.ReadAllRequest
import com.dkprint.app.core.dto.request.SearchRequest
import com.dkprint.app.core.dto.request.UpsertRequest
import com.dkprint.app.core.dto.response.ReadAllResponse
import com.dkprint.app.core.dto.response.ReadResponse
import com.dkprint.app.core.dto.response.SearchResponse
import com.dkprint.app.core.dto.response.UpsertResponse
import com.dkprint.app.core.path.ApiPath
import com.dkprint.app.task.request.application.TaskRequestFacade
import jakarta.validation.constraints.Size
import java.net.URI
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
    companion object {
        private const val IMAGE_SIZE: Int = 5
    }

    @PostMapping(ApiPath.TaskRequest.CREATE, consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart request: UpsertRequest,
        @RequestPart @Size(max = IMAGE_SIZE) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<UpsertResponse> {
        val response: UpsertResponse = taskRequestFacade.create(request, printDesigns)
        return ResponseEntity.created(
            URI.create(ApiPath.TaskRequest.READ.replace("{taskRequestNumber}", response.taskRequestNumber))
        ).body(response)
    }

    @GetMapping(ApiPath.TaskRequest.READ)
    fun read(
        @PathVariable taskRequestNumber: String,
    ): ResponseEntity<ReadResponse> {
        val response: ReadResponse = taskRequestFacade.read(taskRequestNumber)
        return ResponseEntity.ok(response)
    }

    @GetMapping(ApiPath.TaskRequest.READ_ALL)
    fun readAll(
        @ModelAttribute request: ReadAllRequest,
    ): ResponseEntity<Paging<ReadAllResponse>> {
        val response: Paging<ReadAllResponse> = taskRequestFacade.readAll(request)
        return ResponseEntity.ok(response)
    }

    @PatchMapping(ApiPath.TaskRequest.UPDATE, consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(
        @PathVariable taskRequestNumber: String,
        @RequestPart request: UpsertRequest,
        @RequestPart @Size(max = IMAGE_SIZE) printDesigns: List<MultipartFile>?,
    ): ResponseEntity<UpsertResponse> {
        val response: UpsertResponse = taskRequestFacade.update(taskRequestNumber, request, printDesigns)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping(ApiPath.TaskRequest.DELETE)
    fun delete(
        @PathVariable taskRequestNumber: String,
    ): ResponseEntity<Void> {
        taskRequestFacade.delete(taskRequestNumber)
        return ResponseEntity.noContent().build()
    }

    @GetMapping(ApiPath.TaskRequest.SEARCH)
    fun search(
        @ModelAttribute request: SearchRequest,
    ): ResponseEntity<Paging<SearchResponse>> {
        val response: Paging<SearchResponse> = taskRequestFacade.search(request)
        return ResponseEntity.ok(response)
    }
}
