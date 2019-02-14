package cc.kojeve.vielheit.domain

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class User(
        @Column
        val firstName: String
) : Domain()