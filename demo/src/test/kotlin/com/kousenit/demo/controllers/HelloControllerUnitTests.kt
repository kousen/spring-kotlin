package com.kousenit.demo.controllers

import com.kousenit.demo.controllers.HelloController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.validation.support.BindingAwareModelMap

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HelloControllerUnitTests {
    @Test
    internal fun `test sayHello`() {
        val controller = HelloController()
        val model = BindingAwareModelMap()
        val result = controller.sayHello("World", model)
        assert(result == "hello")
        assert(model["user"] == "World")
    }
}