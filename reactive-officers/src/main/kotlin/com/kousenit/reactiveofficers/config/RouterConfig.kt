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
            GET("/route", officerHandler::listOfficers)
            GET("/route/{id}", officerHandler::getOfficer)
            POST("/route", officerHandler::createOfficer)
            DELETE("/route/{id}", officerHandler::deleteOfficer)
        }
    }
}