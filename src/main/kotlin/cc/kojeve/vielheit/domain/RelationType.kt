package cc.kojeve.vielheit.domain

import javax.persistence.*

/**
 * A relation type is an abstract relation
 *
 * For example, an [Entry] might relate to a concept of "place." However, it does not often relate directly to the
 * concept itself -- it relates to a particular place, the [Relation].
 *
 * For an [Entry] to relate to the concept itself, we would have to imagine a relationship type representing the set
 * of all types, for which individual [Relation] instances would represent a particular type, like "place." For most
 * considerations, this would be to esoteric, but our goal is to allow the user to build at least this 3rd order kind
 * of abstraction.
 */
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