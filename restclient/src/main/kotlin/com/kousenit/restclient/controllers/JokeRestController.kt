package com.kousenit.restclient.controllers

import com.kousenit.restclient.services.JokeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class JokeRestController(private val service: JokeService) {

    @GetMapping("/joke")
    suspend fun getResponseAsync(
            @RequestParam(required = false, defaultValue = "Mark") first: String,
            @RequestParam(required = false, defaultValue = "Heckler") last: String) =
        service.getJokeResponse(first, last)
}