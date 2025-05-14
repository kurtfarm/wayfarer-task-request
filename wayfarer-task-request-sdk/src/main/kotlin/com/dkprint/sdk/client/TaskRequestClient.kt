package com.dkprint.sdk.client

import com.dkprint.app.core.dto.response.ReadResponse
import com.dkprint.app.core.path.ApiPath
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class TaskRequestClient(
    private val webClient: WebClient,
) {
    fun findByTaskRequestId(taskRequestId: String): ReadResponse? {
        return webClient.get()
            .uri(ApiPath.TaskRequest.READ, taskRequestId)
            .retrieve()
            .bodyToMono<ReadResponse>()
            .block()
    }
}
