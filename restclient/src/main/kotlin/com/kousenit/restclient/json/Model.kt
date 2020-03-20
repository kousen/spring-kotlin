package com.kousenit.restclient.json

data class JokeValue(val id: Int,
                     val joke: String,
                     val categories: List<String>)

data class JokeResponse(val type: String,
                        val value: JokeValue)

data class Location(val lat: Double,
                    val lng: Double)

data class Geometry(val location: Location)

data class Result(val formattedAddress: String,
                  val geometry: Geometry)

data class Response(val status: String,
                    val results: List<Result>) {
    val location: Location
        get() = results[0].geometry.location

    val formattedAddress: String
        get() = results[0].formattedAddress
}