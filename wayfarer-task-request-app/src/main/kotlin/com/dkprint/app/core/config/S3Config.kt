package com.dkprint.app.core.config

import java.net.URI
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Configuration
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class S3Config(
    @Value("\${s3.endpoint}")
    private val endpoint: String,

    @Value("\${s3.accessKey}")
    private val accessKey: String,

    @Value("\${s3.secretKey}")
    private val secretKey: String,
) {
    @Bean
    fun s3AsyncClient(): S3AsyncClient {
        val uri: URI = URI.create(endpoint)
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)
        val provider: StaticCredentialsProvider = StaticCredentialsProvider.create(credentials)

        return S3AsyncClient.builder()
            .endpointOverride(uri)
            .credentialsProvider(provider)
            .region(Region.AP_NORTHEAST_2)
            .forcePathStyle(true)
            .build()
    }

    @Bean
    fun s3Presigner(): S3Presigner {
        val uri: URI = URI.create(endpoint)
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)
        val provider: StaticCredentialsProvider = StaticCredentialsProvider.create(credentials)

        val config: S3Configuration = S3Configuration.builder()
            .pathStyleAccessEnabled(true)
            .build()

        return S3Presigner.builder()
            .endpointOverride(uri)
            .credentialsProvider(provider)
            .region(Region.AP_NORTHEAST_2)
            .serviceConfiguration(config)
            .build()
    }
}
