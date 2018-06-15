package org.vielheit.core.repository

import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository
import org.vielheit.core.domain.UserNode

@Repository
interface UserNodeRepository : Neo4jRepository<UserNode, Int> {
    fun findByUserId(id: Int): UserNode?
}

