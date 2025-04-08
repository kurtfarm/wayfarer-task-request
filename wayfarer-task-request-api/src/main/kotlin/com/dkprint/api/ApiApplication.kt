package com.dkprint.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan("com.dkprint.app")
@EnableJpaRepositories("com.dkprint.app")
@ComponentScan("com.dkprint.api", "com.dkprint.app")
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
