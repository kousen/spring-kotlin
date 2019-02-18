package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import com.kousenit.persistence.entities.Rank
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.queryForObject
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*

@Suppress("SqlResolve", "SqlNoDataSourceInspection")
@Repository
class JdbcOfficerDAO(val jdbcTemplate: JdbcTemplate) : OfficerDAO {

    private val insertOfficer = SimpleJdbcInsert(jdbcTemplate)
            .withTableName("officers")
            .usingGeneratedKeyColumns("id")

    private val officerMapper = RowMapper { rs: ResultSet, _ ->
        Officer(id = rs.getInt("id"),
                rank = Rank.valueOf(rs.getString("rank")),
                first = rs.getString("first_name"),
                last = rs.getString("last_name"))
    }

    override fun save(officer: Officer): Officer {
        val key = insertOfficer.executeAndReturnKey(
                mapOf("rank" to officer.rank,
                        "first_name" to officer.first,
                        "last_name" to officer.last)) as Int

        officer.id = key
        return officer
    }

    override fun findById(id: Int): Optional<Officer> {
        if (!existsById(id)) return Optional.empty()
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM officers WHERE id=?",
                        officerMapper, id))
    }

    override fun findAll(): List<Officer> =
            jdbcTemplate.query("SELECT * FROM officers") { rs: ResultSet, _ ->
                Officer(id = rs.getInt("id"),
                        rank = Rank.valueOf(rs.getString("rank")),
                        first = rs.getString("first_name"),
                        last = rs.getString("last_name"))
            }

    override fun count() =
            jdbcTemplate.queryForObject<Long>("select count(*) from officers") ?: 0

    override fun delete(officer: Officer) {
        if (officer.id == null) return
        jdbcTemplate.update("DELETE FROM officers WHERE id=?", officer.id)
    }

    override fun existsById(id: Int) =
            jdbcTemplate.queryForObject(
                    "SELECT EXISTS(SELECT 1 FROM officers where id=?)",
                    Boolean::class.java, id)
}