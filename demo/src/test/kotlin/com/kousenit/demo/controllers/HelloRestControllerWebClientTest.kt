package com.kousenit.demo.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloRestControllerWebClientTest(@Autowired val client: WebTestClient) {
    @Test
    fun `greet without name should return 'Hello, World!'`() {
        client.get().uri("/rest")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody<Greeting>()
                .consumeWith { assertEquals("Hello, World!", it.responseBody?.message) }
    }

    @Test
    fun `greet with Dolly returns 'Hello, Dolly!'`() {
        client.get().uri("/rest?name=Dolly")
                .exchange()
                .expectStatus().isOk
                .expectBody<Greeting>()
                .consumeWith { assertEquals("Hello, Dolly!", it.responseBody?.message) }
    }
}