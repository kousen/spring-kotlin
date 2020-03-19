package com.kousenit.demo.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class HelloController {
    @GetMapping("/hello")
    fun sayHello(@RequestParam(required = false,
            defaultValue = "World") name: String = "World",
            model: Model): String {
        model["user"] = name
        return "hello"
    }
}