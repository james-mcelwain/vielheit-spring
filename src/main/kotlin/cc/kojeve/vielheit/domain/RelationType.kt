package cc.kojeve.vielheit.domain

import javax.persistence.*

@Entity
class RelationType(
        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User,
        @Column
        val name: String,
        @OneToMany
        val relations: MutableSet<Relation>
) : Domain()