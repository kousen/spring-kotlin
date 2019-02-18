package com.kousenit.demo.controllers

import org.junit.jupiter.api.Test
import org.springframework.validation.support.BindingAwareModelMap

class HelloControllerUnitTests {
    @Test
    fun testSayHello() {
        val controller = HelloController()
        val model = BindingAwareModelMap()
        val result = controller.sayHello("World", model)
        assert(result == "hello")
        assert(model["user"] == "World")
    }
}