package com.example.gadget

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestClient

@TestConfiguration
class TestRestClientConfig {
    @Bean
    fun restClientBuilder(): RestClient.Builder = RestClient.builder()
}
