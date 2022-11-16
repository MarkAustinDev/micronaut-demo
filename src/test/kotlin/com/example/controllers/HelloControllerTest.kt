package com.example.controllers

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@MicronautTest
class HelloControllerTest {
    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Nested
    inner class EndpointTests {
        @Test
        fun `should return a message when a  GET request is made to an endpoint that exists`() {
            val request: HttpRequest<Any> = HttpRequest.GET("/hello")
            val body = client.toBlocking().retrieve(request)
            assertNotNull(body)
            assertEquals("Hello World", body)
        }
    }

    @Nested
    inner class ExceptionTests {
        @Test
        fun `should throw an exception when a GET request is made to an endpoint that does not exist`() {
            val request: HttpRequest<Any> = HttpRequest.GET("/incorrect-path")
            val result = assertThrows<HttpClientResponseException> {
                client.toBlocking().retrieve(request)
            }
            assertEquals("Not Found", result.message)
        }
    }
}