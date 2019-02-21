package cc.kojeve.vielheit.domain

import javax.persistence.*

@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name", "user_id"])])
@Entity
class Tag(
        @Column
        val name: String
): Domain() {
        @ManyToMany(mappedBy = "tags")
        lateinit var entries: Set<Entry>
        @ManyToOne
        @JoinColumn(name = "user_id")
        lateinit var user: User
}