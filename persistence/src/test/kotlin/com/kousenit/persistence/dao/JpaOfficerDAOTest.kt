package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import com.kousenit.persistence.entities.Rank
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class JpaOfficerDAOTest(@Autowired val dao: JpaOfficerDAO,
                                 @Autowired private val template: JdbcTemplate) {

    private fun getIds() =
            template.query("select id from officers") { rs, _ ->
                rs.getInt("id")
            }

    @Test
    fun `save should return officer with non-null id`() {
        Officer(rank = Rank.LIEUTENANT, first = "Nyota", last = "Uhuru")
                .apply { dao.save(this) }
                .also { assertNotNull(it.id) }
    }

    @Test
    internal fun `findById works for all ids in table`() {
        getIds().forEach { id ->
            dao.findById(id)
                    .also {
                        assertTrue(it.isPresent)
                        assertEquals(id, it.get().id)
                    }
        }
    }

    @Test
    internal fun `officer with id 999 does NOT exist`() {
        assertFalse(dao.findById(999).isPresent)
    }

    @Test
    internal fun `should be 5 officers in test db`() {
        assertEquals(5L, dao.count())
    }

    @Test
    fun `check officer last names in test db`() {
        dao.findAll()
                .map { it.last }
                .also { lastNames ->
                    assertThat(lastNames, containsInAnyOrder(
                            "Archer", "Janeway", "Kirk", "Picard", "Sisko"))
                }
    }

    @Test
    fun `delete all officers`() {
        getIds().forEach { id ->
            dao.findById(id)
                    .run {
                        assertTrue(this.isPresent)
                        dao.delete(this.get())
                    }
        }
        assertEquals(0L, dao.count())
    }

    @Test
    fun `officers with ids in db exist`() {
        getIds().forEach { id ->
            assertTrue(dao.existsById(id))
        }
    }
}