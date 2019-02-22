package cc.kojeve.vielheit.repository

import cc.kojeve.vielheit.domain.Entry
import cc.kojeve.vielheit.domain.Tag
import cc.kojeve.vielheit.domain.User
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

interface EntryRepository : CrudRepository<Entry, Long> {
    fun findByUser(user: User): Set<Entry>

    fun findByUserAndTags(user: User, tag: Tag): Set<Entry>

}