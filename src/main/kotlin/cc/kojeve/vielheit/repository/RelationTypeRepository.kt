package cc.kojeve.vielheit.repository

import org.springframework.data.repository.CrudRepository
import cc.kojeve.vielheit.domain.RelationType

interface RelationTypeRepository : CrudRepository<RelationType, Long> {
}