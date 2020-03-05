package com.kousenit.kotlindemo.services

import org.springframework.stereotype.Service

@Service
class GreetingService {
    fun sayHello(name: String) = "Hello, $name!"
}