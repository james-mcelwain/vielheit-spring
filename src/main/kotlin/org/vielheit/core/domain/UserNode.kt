package org.vielheit.core.domain

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.Index
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity(label = "User")
class UserNode {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Index(unique = true)
    var userId: Int? = null
}