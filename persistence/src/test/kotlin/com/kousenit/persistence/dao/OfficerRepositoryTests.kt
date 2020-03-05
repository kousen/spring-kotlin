package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import com.kousenit.persistence.entities.Rank
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.transaction.annotation.Transactional

@Suppress("SqlResolve", "SqlNoDataSourceInspection")
@SpringBootTest
@Transactional
class OfficerRepositoryTests {

    @Autowired
    lateinit var dao: OfficerRepository

    @Autowired
    lateinit var template: JdbcTemplate

    private val idMapper = RowMapper { rs, _ -> rs.getInt("id") }

    @Test
    fun `save should return officer with non-empty id`() {
        val uhuru = Officer(rank = Rank.LIEUTENANT, first = "Nyota", last = "Uhuru")
        val officer = dao.save(uhuru)
        assert(officer.id != null)
    }

    @Test
    fun `findById works for all ids in table`() {
        template.query("select id from officers", idMapper).forEach { id ->
            val officer = dao.findById(id)
            assert(officer.isPresent)
            assert(officer.get().id == id)
        }
    }

    @Test
    fun `officer with id 999 does not exist`() {
        val officer = dao.findById(999)
        assert(!officer.isPresent)
    }

    @Test
    fun `should be 5 officers in test db`() {
        assert(5L == dao.count())
    }

    @Test
    fun `check officer last names in test db`() {
        val lastNames = dao.findAll().map { it.last }
        assertThat(lastNames,
                containsInAnyOrder("Archer", "Janeway", "Kirk", "Picard", "Sisko"))
    }

    @Test
    fun `delete all officers`() {
        template.query("select id from officers", idMapper).forEach { id ->
            val officer = dao.findById(id)
            assert(officer.isPresent)

            dao.delete(officer.get())
        }

        assert(dao.count() == 0L)
    }

    @Test
    fun `existsById with ids in table returns true`() {
        template.query("select id from officers", idMapper).forEach { id ->
            assert(dao.existsById(id))
        }
    }

    @Test
    fun `there are five captains in the sample db`() {
        val captains = dao.findAllByRank(Rank.CAPTAIN)
        assert(captains.size == 5)
    }

    @Test
    fun `captains with last names containing i`() {
        val lastNames = dao.findAllByLastLikeAndRankEquals("%i%", Rank.CAPTAIN)
                .map { it.last }
        assertThat(lastNames, containsInAnyOrder("Kirk", "Picard", "Sisko"))
    }
}