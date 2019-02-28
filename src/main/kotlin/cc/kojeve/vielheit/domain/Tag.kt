package cc.kojeve.vielheit.domain

import javax.persistence.*

/**
 * Tags are metadata that may or may not be useful for grouping entries.
 *
 * For example, we can imagine a [RelationType] "Time" that creates meaningful associations to certain
 * periods or eras, but we could also have a much more functional or pragmatic time based tag that represents that
 * year, month, or moment the entry was written.
 */
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
) : Domain()