package com.kousenit.restclient.services

import com.kousenit.restclient.json.JokeResponse
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class JokeService(builder: RestTemplateBuilder) {
    val restTemplate: RestTemplate = builder.build()

    companion object {
        const val BASE = "http://api.icndb.com/jokes/random?limitTo=[nerdy]"
    }

    fun getJoke(first: String, last: String): String {
        val url = "$BASE&firstName=$first&lastName=$last"
        val response = restTemplate.getForObject<JokeResponse>(url)
        return response?.value?.joke ?: "No joke returned"
    }
}