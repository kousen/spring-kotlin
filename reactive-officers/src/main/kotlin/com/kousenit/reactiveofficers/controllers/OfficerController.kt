package com.kousenit.reactiveofficers.controllers

import com.kousenit.reactiveofficers.dao.OfficerRepository
import com.kousenit.reactiveofficers.entities.Officer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/officers")
class OfficerController(val repository: OfficerRepository) {
    @GetMapping
    fun getAllOfficers() = repository.findAll()

    @GetMapping("{id}")
    fun getOfficer(@PathVariable id: String) = repository.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveOfficer(@RequestBody officer: Officer) = repository.save(officer)

    @PutMapping("{id}")
    fun updateOfficer(@PathVariable id: String, @RequestBody officer: Officer) =
        repository.findById(id)
                .flatMap { existingOfficer ->
                    existingOfficer.rank = officer.rank
                    existingOfficer.first = officer.first
                    existingOfficer.last = officer.last
                    repository.save(existingOfficer)
                }
                .map { ResponseEntity(it, HttpStatus.OK) }
                .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOfficer(@PathVariable id: String) =
        repository.deleteById(id)
                .then(Mono.just(ResponseEntity<Unit>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))

    @DeleteMapping
    fun deleteAllOfficers() = repository.deleteAll()
}