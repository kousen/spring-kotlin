package com.kousenit.restclient.controllers

import com.kousenit.restclient.json.JokeResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class JokeRestControllerTest(@Autowired val client: WebTestClient) {

    @Test
    fun testJokeAsync() {
        client.get()
                .uri("/joke")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody<JokeResponse>()
                .consumeWith { println(it.responseBody?.value?.joke) }
    }
}