package com.kousenit.restclient.services

import com.kousenit.restclient.entities.Site
import com.kousenit.restclient.json.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import java.net.URLEncoder
import java.time.Duration

@Service
class GeocoderService(@Autowired builder: RestTemplateBuilder) {
    private val restTemplate = builder
            .setConnectTimeout(Duration.ofSeconds(2))
            .build()

    companion object {
        const val BASE = "https://maps.googleapis.com/maps/api/geocode/json"
        const val KEY = "AIzaSyDw_d6dfxDEI7MAvqfGXEIsEMwjC1PWRno"
    }

    fun getLatLng(vararg address: String): Site {
        val encoded = address.joinToString(",") {
            URLEncoder.encode(it, "UTF-8")
        }
        val url = "$BASE?address=$encoded&key=$KEY"
        return restTemplate.getForObject<Response>(url).let {
            Site(name = it.formattedAddress,
                    latitude = it.location.lat,
                    longitude = it.location.lng)
        }
    }
}