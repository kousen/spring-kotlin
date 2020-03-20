package com.kousenit.demo.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloRestController {

    @GetMapping("/rest")
    fun greet(@RequestParam(required = false, defaultValue = "World")
              name: String) = Greeting("Hello, $name!")
}

data class Greeting(val message: String)