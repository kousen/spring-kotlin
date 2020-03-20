package com.kousenit.restclient.services

import com.kousenit.restclient.json.JokeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import java.time.Duration

@Service
class JokeService(@Autowired builder: RestTemplateBuilder,
                @Autowired private val clientBuilder: WebClient.Builder) {
    private val restTemplate = builder
            .setConnectTimeout(Duration.ofSeconds(2))
            .build()

    private val client = clientBuilder
            .baseUrl("http://api.icndb.com/jokes/random")
            .build()

    companion object {
        const val BASE = "http://api.icndb.com/jokes/random?limitTo=[nerdy]"
    }

    fun getJoke(first: String, last: String) =
            restTemplate.getForObject<JokeResponse>("$BASE&firstName=$first&lastName=$last")
                    .value.joke

    suspend fun getJokeResponse(first: String, last: String) =
            withContext(Dispatchers.IO) {
                client.get()
                        .uri("$BASE&firstName={first}&lastName={last}", first, last)
                        .awaitExchange()
                        .awaitBody<JokeResponse>()
            }
}