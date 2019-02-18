package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import com.kousenit.persistence.entities.Rank
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class JdbcOfficerDAOTests {

    @Autowired
    @Qualifier("jdbcOfficerDAO")
    lateinit var dao: OfficerDAO

    @Test
    fun `save should return officer with non-empty id`() {
        val uhuru = Officer(rank = Rank.LIEUTENANT, first = "Nyota", last = "Uhuru")
        val officer = dao.save(uhuru)
        assert(officer.id != null)
    }

    @Test
    fun `officer with id 1 exists`() {
        val officer = dao.findById(1)
        assert(officer.isPresent)
        assert(officer.get().id == 1)
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
        (1..5).forEach { id ->
            val officer = dao.findById(id)
            assert(officer.isPresent)

            dao.delete(officer.get())
        }

        assert(dao.count() == 0L)
    }

    @Test
    fun `officers with ids 1 through 5 exist`() {
        (1..5).forEach {
            assert(dao.existsById(it))
        }
    }
}