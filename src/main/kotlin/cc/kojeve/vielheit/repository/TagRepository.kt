package cc.kojeve.vielheit.repository

import cc.kojeve.vielheit.domain.Tag
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

interface TagRepository: CrudRepository<Tag, Long> {
}