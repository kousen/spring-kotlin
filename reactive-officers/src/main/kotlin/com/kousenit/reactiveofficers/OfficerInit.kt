package com.kousenit.reactiveofficers

import com.kousenit.reactiveofficers.dao.OfficerRepository
import com.kousenit.reactiveofficers.entities.Officer
import com.kousenit.reactiveofficers.entities.Rank
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class OfficerInit(val repository: OfficerRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        repository.deleteAll()
                .thenMany(Flux.just(Officer(rank = Rank.CAPTAIN, first = "James", last = "Kirk"),
                        Officer(rank = Rank.CAPTAIN, first = "Jean-Luc", last = "Picard"),
                        Officer(rank = Rank.CAPTAIN, first = "Benjamin", last = "Sisko"),
                        Officer(rank = Rank.CAPTAIN, first = "Kathryn", last = "Janeway"),
                        Officer(rank = Rank.CAPTAIN, first = "Jonathan", last = "Archer")))
                .flatMap { repository.save(it) }
                .subscribe { println(it) }
    }
}