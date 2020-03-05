package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import com.kousenit.persistence.entities.Rank
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class JdbcOfficerDAOTest(@Autowired val dao: JdbcOfficerDAO) {
    @Test
    fun `save should return officer with non-null id`() {
        Officer(rank = Rank.LIEUTENANT, first = "Nyota", last = "Uhuru")
                .apply { dao.save(this) }
                .also { assertNotNull(it.id) }
    }

    @Test
    internal fun `officer with id 1 exists`() {
        dao.findById(1)
                .also {
                    assertTrue(it.isPresent)
                    assertEquals(1, it.get().id)
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
        (1..5).forEach { id ->
                    dao.findById(id)
                            .run {
                                assertTrue(this.isPresent)
                                dao.delete(this.get())
                            }
                }
        assertEquals(0L, dao.count())
    }

    @Test
    fun `officers with ids 1 through 5 exist`() {
        (1..5).forEach { id ->
            assertTrue(dao.existsById(id))
        }
    }
}