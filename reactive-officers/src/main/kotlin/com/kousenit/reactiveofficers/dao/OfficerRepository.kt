package com.kousenit.reactiveofficers.dao

import com.kousenit.reactiveofficers.entities.Officer
import com.kousenit.reactiveofficers.entities.Rank
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface OfficerRepository : ReactiveMongoRepository<Officer, String> {
    fun findAllByRank(rank: Rank): Flux<Officer>
    fun findAllByLast(last: String): Flux<Officer>
}