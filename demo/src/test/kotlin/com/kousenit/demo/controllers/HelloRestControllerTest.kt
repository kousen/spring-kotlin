package com.kousenit.demo.controllers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.getForObject
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class HelloRestControllerTest(@Autowired val template: TestRestTemplate) {

    @Test
    fun `greet without name should return 'Hello, World!'`() {
        val entity = template.getForEntity<Greeting>("/rest")

        assertEquals(HttpStatus.OK, entity.statusCode)

        entity.headers.contentType?.let {
            assertTrue(it == MediaType.APPLICATION_JSON)
        } ?: fail("Content type header not application/json")

        assertEquals("Hello, World!", entity.body?.message)
    }

    @Test
    fun `greet with Dolly returns 'Hello, Dolly!'`() {
        val greeting = template.getForObject<Greeting>("/rest?name=Dolly")
        assertEquals("Hello, Dolly!", greeting?.message)
    }
}