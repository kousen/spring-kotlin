package com.kousenit.kotlindemo.controllers

import com.kousenit.kotlindemo.entities.Greeting
import com.kousenit.kotlindemo.services.GreetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    @Autowired
    lateinit var service: GreetingService

    @GetMapping("/json")
    fun greetUser(@RequestParam name: String?) =
            if (name != null) Greeting(service.sayHello(name)) else Greeting()
}