package cc.kojeve.vielheit.domain

import cc.kojeve.vielheit.dto.UserData
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType

@Entity
class User(
        @Column(unique = true)
        val username: String,
        @Column
        val password: String,
        @ElementCollection(fetch = FetchType.EAGER)
        val roles: List<Role>

) : Domain()