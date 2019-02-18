package com.kousenit.demo.controllers

import com.kousenit.demo.entities.Greeting
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.getForObject
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloRestControllerTests(@Autowired val template: TestRestTemplate) {

    @Test
    internal fun `greet without name should return 'Hello, World!'`() {
        val entity = template.getForEntity<Greeting>("/rest")

        assert(entity.statusCode == HttpStatus.OK)
        entity.headers.contentType?.let {
            assert(it.equals(MediaType.APPLICATION_JSON_UTF8))
        }
        assert(entity.body?.message == "Hello, World!")
    }

    @Test
    internal fun `greet with Dolly returns 'Hello, Dolly!'`() {
        val greeting = template.getForObject<Greeting>("/rest?name=Dolly")
        assert(greeting?.message == "Hello, Dolly!")
    }
}