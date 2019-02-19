package com.kousenit.reactiveofficers.config

import com.kousenit.reactiveofficers.controllers.OfficerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig {

    @Bean
    fun router(officerHandler: OfficerHandler) = router {
        accept(MediaType.APPLICATION_JSON).nest {
            "/route".nest {
                GET("/", officerHandler::listOfficers)
                GET("/{id}", officerHandler::getOfficer)
                POST("/", officerHandler::createOfficer)
                DELETE("/{id}", officerHandler::deleteOfficer)
            }
        }
    }
}