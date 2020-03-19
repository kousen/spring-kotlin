package com.kousenit.demo.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.springframework.validation.support.BindingAwareModelMap

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HelloControllerUnitTests {
    @Test
    fun `test sayHello without a name`() {
        val controller = HelloController()
        val model = BindingAwareModelMap()
        val result = controller.sayHello(model = model)
        assertAll(
                { assertEquals("hello", result) },
                { assertEquals("World", model["user"]) }
        )
    }

    @Test
    fun `test sayHello with a name`() {
        val controller = HelloController()
        val model = BindingAwareModelMap()
        val result = controller.sayHello("World", model)
        assertAll(
                { assertEquals("hello", result) },
                { assertEquals("World", model["user"]) }
        )
    }
}