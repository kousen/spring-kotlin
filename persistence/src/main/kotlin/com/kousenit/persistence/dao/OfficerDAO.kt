package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import java.util.*

interface OfficerDAO {
    fun save(officer: Officer): Officer
    fun findById(id: Int): Optional<Officer>
    fun findAll(): List<Officer>
    fun count(): Long
    fun delete(officer: Officer)
    fun existsById(id: Int): Boolean
}