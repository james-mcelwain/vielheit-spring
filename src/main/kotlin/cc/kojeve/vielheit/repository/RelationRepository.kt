package cc.kojeve.vielheit.repository

import cc.kojeve.vielheit.domain.Relation
import org.springframework.data.repository.CrudRepository

interface RelationRepository : CrudRepository<Relation, Long> {
}