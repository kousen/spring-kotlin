package com.kousenit.restclient.services

import com.kousenit.restclient.entities.Site
import com.kousenit.restclient.json.Response
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject
import java.net.URLEncoder

@Service
class GeocoderService(builder: RestTemplateBuilder) {
    val restTemplate = builder.build()

    companion object {
        const val BASE = "https://maps.googleapis.com/maps/api/geocode/json"
        const val KEY = "AIzaSyDw_d6dfxDEI7MAvqfGXEIsEMwjC1PWRno"
    }

    fun getLatLng(vararg address: String): Site {
        val encoded = address.map { URLEncoder.encode(it, "UTF-8") }.joinToString(",")
        val url = "$BASE?address=$encoded&key=$KEY"
        val response = restTemplate.getForObject<Response>(url)
        response?.let {
            return Site(name = response.formattedAddress,
                    latitude = response.location.lat,
                    longitude = response.location.lng)
        }
        return Site("No data", 0.0, 0.0)
    }
}