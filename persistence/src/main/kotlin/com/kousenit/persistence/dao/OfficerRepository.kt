package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import com.kousenit.persistence.entities.Rank
import org.springframework.data.jpa.repository.JpaRepository

interface OfficerRepository : JpaRepository<Officer, Int> {
    fun findAllByRank(rank: Rank): List<Officer>
    fun findAllByLastLikeAndRankEquals(like: String, rank: Rank): List<Officer>
}