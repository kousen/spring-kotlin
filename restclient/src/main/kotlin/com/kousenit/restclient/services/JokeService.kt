package com.kousenit.restclient.services

import com.kousenit.restclient.json.JokeResponse
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class JokeService(builder: RestTemplateBuilder) {
    val restTemplate: RestTemplate = builder.build()
    val client: WebClient
        get() = WebClient.create("http://api.icndb.com")

    companion object {
        const val BASE = "http://api.icndb.com/jokes/random?limitTo=[nerdy]"
        const val path = "/jokes/random?limitTo=[nerdy]&firstName={first}&lastName={last}"
    }

    fun getJoke(first: String, last: String): String {
        val url = "$BASE&firstName=$first&lastName=$last"
        val response = restTemplate.getForObject<JokeResponse>(url)
        return response?.value?.joke ?: "No joke returned"
    }

    fun getJokeAsync(first: String, last: String): Mono<String> =
            client.get()
                    .uri(path, first, last)
                    .retrieve()
                    .bodyToMono<JokeResponse>()
                    .map { response ->
                        response?.value?.joke ?: "No joke returned"
                    }
}