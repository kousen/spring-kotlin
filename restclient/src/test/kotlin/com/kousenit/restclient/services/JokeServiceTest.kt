package com.kousenit.restclient.services


import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier
import java.time.Duration

@SpringBootTest
class JokeServiceTest() {
    @Autowired
    lateinit var service: JokeService

    val logger = LoggerFactory.getLogger(JokeServiceTest::class.java)

    @Test
    fun `joke with Craig Walls should have the words Craig or Walls`() {
        val joke = service.getJoke("Craig", "Walls")
        logger.info(joke)
        assert(joke.contains("Craig") || joke.contains("Walls") ||
            joke.contains("No joke returned"))
    }

    @Test
    fun `asyc joke function returns Mono of type String`() {
        val joke = service.getJokeAsync("Matt", "Stine")
                .block(Duration.ofSeconds(2))
        logger.info(joke)
        if (joke != null) {
            assert(joke.contains("Matt") || joke.contains("Stine") ||
                    joke.contains("No joke returned"))
        }
    }

    @Test
    fun `verify async joke function using StepVerifier`() {
        StepVerifier.create(service.getJokeAsync("Nate","Schutta"))
                .assertNext { joke ->
                    logger.info(joke)
                    assert(joke.contains("Nate") || joke.contains("Schutta") ||
                            joke.contains("No joke returned"))
                }
                .verifyComplete()
    }
}