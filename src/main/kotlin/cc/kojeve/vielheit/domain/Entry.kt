package cc.kojeve.vielheit.domain

import javax.persistence.*

@Entity
class Entry(
        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User,
        @Column
        val title: String,
        @ManyToMany(mappedBy = "entries", cascade = [CascadeType.ALL])
        val tags: MutableSet<Tag> = mutableSetOf(),
        @ManyToMany(mappedBy = "entries", cascade = [CascadeType.ALL])
        val relations: MutableSet<Relation> = mutableSetOf()
) : Domain()