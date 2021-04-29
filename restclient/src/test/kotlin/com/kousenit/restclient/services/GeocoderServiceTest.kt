package com.kousenit.restclient.services

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.closeTo
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class GeocoderServiceTest(@Autowired val service: GeocoderService) {
    val logger: Logger = LoggerFactory.getLogger(GeocoderServiceTest::class.java)

    @Test
    fun `lat,lng of Boston, MA`() =
            service.getLatLng("Boston", "MA")
                    .also { logger.info(it.toString()) }
                    .run {
                        assertThat(this.latitude, `is`(closeTo(42.36, 0.01)))
                        assertThat(this.longitude, `is`(closeTo(-71.06, 0.01)))
                    }

    @Test
    fun `lat,lng of Google headquarters`() =
            service.getLatLng("1600 Ampitheatre Parkway", "Mountain View", "CA")
                    .also { logger.info(it.toString()) }
                    .run {
                        assertThat(this.latitude, `is`(closeTo(37.43, 0.01)))
                        assertThat(this.longitude, `is`(closeTo(-122.08, 0.02)))
                    }
}
