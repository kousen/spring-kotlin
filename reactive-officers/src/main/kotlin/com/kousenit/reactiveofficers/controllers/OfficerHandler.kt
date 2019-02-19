package com.kousenit.reactiveofficers.controllers

import com.kousenit.reactiveofficers.dao.OfficerRepository
import com.kousenit.reactiveofficers.entities.Officer
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.noContent
import org.springframework.web.reactive.function.server.body

@Component
class OfficerHandler(private val repository: OfficerRepository) {

    fun listOfficers(request: ServerRequest) =
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(repository.findAll())

    fun getOfficer(request: ServerRequest) =
            ServerResponse.ok()
                    .body(repository.findById(request.pathVariable("id")))
                    .switchIfEmpty(ServerResponse.notFound().build())

    fun createOfficer(request: ServerRequest) =
            request.bodyToMono(Officer::class.java)
                    .flatMap { item ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(repository.save(item))
                    }

    fun deleteOfficer(request: ServerRequest) =
            repository.deleteById(request.pathVariable("id"))
                    .flatMap { ServerResponse.noContent().build() }
}