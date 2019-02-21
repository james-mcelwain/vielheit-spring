package cc.kojeve.vielheit.domain

import javax.persistence.*

@Entity
class Entry(
        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User,
        @Column
        val title: String,
        @ManyToMany
        val tags: MutableSet<Tag> = mutableSetOf()
) : Domain()