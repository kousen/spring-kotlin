package com.kousenit.demo.controllers

import org.springframework.web.bind.annotation.RestController
import com.kousenit.demo.entities.Greeting
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@RestController
class HelloRestController {
    @GetMapping("/rest")
    fun greet(@RequestParam(required = false, defaultValue = "World")
              name: String) = Greeting("Hello, $name!")
}