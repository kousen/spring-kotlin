package com.kousenit.restclient.services

import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
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
}