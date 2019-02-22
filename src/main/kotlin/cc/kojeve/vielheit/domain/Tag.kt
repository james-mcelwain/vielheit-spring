package cc.kojeve.vielheit.domain

import javax.persistence.*

@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name", "user_id"])])
@Entity
class Tag(
        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User,
        @Column
        val name: String,
        @ManyToMany
        val entries: MutableSet<Entry> = mutableSetOf()
): Domain()