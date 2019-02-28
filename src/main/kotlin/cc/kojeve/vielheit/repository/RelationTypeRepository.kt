package cc.kojeve.vielheit.repository

import cc.kojeve.vielheit.domain.RelationType
import org.springframework.data.repository.CrudRepository

interface RelationTypeRepository : CrudRepository<RelationType, Long> {
}