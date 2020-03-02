package com.kousenit.restclient.services

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.closeTo
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GeocoderServiceTest() {
    @Autowired
    lateinit var service: GeocoderService

    val logger = LoggerFactory.getLogger(GeocoderServiceTest::class.java)

    @Test
    fun `lat,lng of Boston, MA`() {
        val site = service.getLatLng("Boston", "MA")
        logger.info(site.toString())
        assertThat(site.latitude, `is`(closeTo(42.36, 0.01)))
        assertThat(site.longitude, `is`(closeTo(-71.06, 0.01)))
    }

    @Test
    fun `lat,lng of Google headquarters`() {
        val site = service.getLatLng("1600 Ampitheatre Parkway", "Mountain View", "CA")
        logger.info(site.toString())
        assertThat(site.latitude, `is`(closeTo(37.42, 0.01)))
        assertThat(site.longitude, `is`(closeTo(-122.1, 0.02)))
    }
}