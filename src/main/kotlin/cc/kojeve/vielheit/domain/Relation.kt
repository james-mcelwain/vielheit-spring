package cc.kojeve.vielheit.domain

import javax.persistence.*

@Entity
class Relation(
        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User,
        @ManyToOne
        @JoinColumn(name = "type_id")
        val type: RelationType,
        @Column
        val name: String,
        @ManyToMany
        val entries: MutableSet<Entry> = mutableSetOf()
) : Domain()