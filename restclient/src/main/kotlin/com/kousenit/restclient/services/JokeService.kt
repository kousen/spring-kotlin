package com.kousenit.restclient.services

import com.kousenit.restclient.json.JokeResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import java.time.Duration

@Service
class JokeService(@Autowired builder: RestTemplateBuilder) {
    private val restTemplate = builder
            .setConnectTimeout(Duration.ofSeconds(2))
            .build()

    companion object {
        const val BASE = "http://api.icndb.com/jokes/random?limitTo=[nerdy]"
    }

    fun getJoke(first: String, last: String) =
            restTemplate.getForObject<JokeResponse>("$BASE&firstName=$first&lastName=$last")
                    .value.joke
}