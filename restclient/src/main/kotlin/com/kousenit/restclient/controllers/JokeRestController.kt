package com.kousenit.restclient.controllers

import com.kousenit.restclient.services.JokeService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class JokeRestController(val service: JokeService) {

    @GetMapping("/joke")
    fun getResponseAsync(
            @RequestParam(required = false, defaultValue = "Mark") first: String,
            @RequestParam(required = false, defaultValue = "Heckler") last: String) =
            GlobalScope.async {
                service.getJokeResponse(first, last)
            }

}