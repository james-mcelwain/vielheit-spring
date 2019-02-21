package cc.kojeve.vielheit.domain

import cc.kojeve.vielheit.dto.UserData
import javax.persistence.*

@Entity
data class User(
        @Column(unique = true)
        val username: String,
        @Column
        val password: String,
        @ElementCollection(fetch = FetchType.EAGER)
        val roles: List<Role> = emptyList(),
        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
        val tags: Set<Tag> = emptySet(),
        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
        var entries: MutableSet<Entry> = mutableSetOf()
) : Domain()