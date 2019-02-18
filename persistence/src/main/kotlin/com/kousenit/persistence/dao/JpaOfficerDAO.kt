package com.kousenit.persistence.dao

import com.kousenit.persistence.entities.Officer
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Suppress("JpaQlInspection")
@Repository
class JpaOfficerDAO(@PersistenceContext val entityManager: EntityManager) : OfficerDAO {

    override fun save(officer: Officer): Officer {
        entityManager.persist(officer)
        return officer
    }

    override fun findById(id: Int) =
            Optional.ofNullable(entityManager.find(Officer::class.java, id))

    override fun findAll(): List<Officer> =
            entityManager.createQuery("select o from Officer o", Officer::class.java)
                    .resultList

    override fun count() =
            entityManager.createQuery("select count(o) from Officer o")
                    .singleResult as Long


    override fun delete(officer: Officer) =
            entityManager.remove(officer)

    override fun existsById(id: Int) =
        entityManager.createQuery(
                "SELECT 1 from Officer o where o.id=:id")
                .setParameter("id", id)
                .getSingleResult() != null
}