package com.kousenit.restclient.services

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class JokeServiceTest(@Autowired val service: JokeService) {
    val logger: Logger = LoggerFactory.getLogger(JokeServiceTest::class.java)

    @Test
    internal fun `joke using Craig Walls should have the words Craig or Walls`() {
        val joke = service.getJoke("Craig", "Walls")
        logger.info(joke)
        assertTrue(joke.contains("Craig") || joke.contains("Walls"))
    }
}
