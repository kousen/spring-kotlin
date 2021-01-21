package com.kousenit.reactiveofficers.controllers

import com.kousenit.reactiveofficers.dao.OfficerRepository
import com.kousenit.reactiveofficers.entities.Officer
import com.kousenit.reactiveofficers.entities.Rank
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OfficerHandlerAndRouterTest(
    @Autowired var client: WebTestClient,
    @Autowired var repository: OfficerRepository
) {

    private val officers = listOf(
        Officer(Rank.CAPTAIN, "James", "Kirk"),
        Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
        Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
        Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
        Officer(Rank.CAPTAIN, "Jonathan", "Archer")
    )

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
            .thenMany(Flux.fromIterable(officers))
            .flatMap { repository.save(it) }
            .doOnNext { println(it) }
            .then()
            .block()
    }

    @Test
    fun `GET to route returns all officers in db`() {
        client.get().uri("/route")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(Officer::class.java)
            .hasSize(5)
    }

    @Test
    fun `GET with id returns that officer`() {
        client.get().uri("/route/{id}", officers[0].id)
            .exchange()
            .expectStatus().isOk
            .expectBody(Officer::class.java)
    }

    @Test
    fun `POST to route creates a new officer`() {
        val officer = Officer(Rank.LIEUTENANT, "Hikaru", "Sulu")

        client.post().uri("/route")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(officer))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.first").isEqualTo("Hikaru")
            .jsonPath("$.last").isEqualTo("Sulu")
    }
}