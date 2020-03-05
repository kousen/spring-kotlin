package com.kousenit.kotlindemo.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GreetingHTMLController {

    @GetMapping("/hello")
    fun sayHello(@RequestParam(required = false,
            defaultValue = "World") name: String, model: Model): String {
        model["user"] = name
        return "hello"
    }
}