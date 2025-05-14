package com.dkprint.sdk.config

import com.dkprint.sdk.client.TaskRequestClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class TaskRequestConfig {
    @Bean
    @ConditionalOnMissingBean
    fun taskRequestClient(webClient: WebClient): TaskRequestClient {
        return TaskRequestClient(webClient)
    }
}
