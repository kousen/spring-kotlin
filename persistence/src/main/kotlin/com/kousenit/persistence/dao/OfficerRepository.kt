package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import org.springframework.data.jpa.repository.JpaRepository

interface OfficerRepository : JpaRepository<Officer, Int> {
}