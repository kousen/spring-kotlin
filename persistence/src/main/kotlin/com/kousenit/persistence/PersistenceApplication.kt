package com.kousenit.persistence

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PersistenceApplication

fun main(args: Array<String>) {
    runApplication<PersistenceApplication>(*args)
}
