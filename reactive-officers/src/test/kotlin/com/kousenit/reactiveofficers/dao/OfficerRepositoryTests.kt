package com.kousenit.reactiveofficers.dao

import com.kousenit.reactiveofficers.entities.Officer
import com.kousenit.reactiveofficers.entities.Rank
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@SpringBootTest
class OfficerRepositoryTests {

    @Autowired
    lateinit var repository: OfficerRepository

    private val officers = listOf(
            Officer(Rank.CAPTAIN, "James", "Kirk"),
            Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
            Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
            Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
            Officer(Rank.CAPTAIN, "Jonathan", "Archer"))

    @Before
    fun setUp() {
        repository.deleteAll()
                .thenMany(Flux.fromIterable(officers))
                .flatMap { repository.save(it) }
                .then()
                .block()
    }

    @Test
    fun `save officer should give nonempty id`() {
        val lorca = Officer(Rank.CAPTAIN, "Gabriel", "Lorca")
        StepVerifier.create(repository.save(lorca))
                .expectNextMatches { it.id != "" }
                .verifyComplete()
    }

    @Test
    fun `findAll returns 5 officers`() {
        StepVerifier.create(repository.findAll())
                .expectNextCount(5)
                .verifyComplete()
    }

    @Test
    fun `officers exist in db for each updated id`() {
        officers.map { it.id ?: "" }.forEach { id ->
            StepVerifier.create(repository.findById(id))
                    .expectNextCount(1)
                    .verifyComplete()
        }
    }

    @Test
    fun `no officer for id xyz`() {
        StepVerifier.create(repository.findById("xyz"))
                .verifyComplete()
    }

    @Test
    fun `count returns 5`() {
        StepVerifier.create(repository.count())
                .expectNext(5)
                .verifyComplete()
    }

    @Test
    fun `db contains only captains`() {
        StepVerifier.create(
                repository.findAllByRank(Rank.CAPTAIN)
                        .map { it.rank }
                        .distinct())
                .expectNextCount(1)
                .verifyComplete()

        StepVerifier.create(
                repository.findAllByRank(Rank.ENSIGN)
                        .map { it.rank }
                        .distinct())
                .verifyComplete()
    }

    @Test
    fun `officer in db exists for each last name`() {
        officers.map { it.last }
                .forEach { lastName ->
                    StepVerifier.create(repository.findAllByLast(lastName))
                            .expectNextMatches { officer ->
                                officer.last == lastName
                            }
                            .verifyComplete()
                }
    }
}