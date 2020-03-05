package com.kousenit.demo.controllers

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

        assert(entity.statusCode == HttpStatus.OK)
        entity.headers.contentType?.let {
            assert(it.equals(MediaType.APPLICATION_JSON))
        }
        assert(entity.body?.message == "Hello, World!")
    }

    @Test
    fun `greet with Dolly returns 'Hello, Dolly!'`() {
        val greeting = template.getForObject<Greeting>("/rest?name=Dolly")
        assert(greeting?.message == "Hello, Dolly!")
    }
}