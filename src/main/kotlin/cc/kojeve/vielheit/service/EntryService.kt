package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.domain.Entry
import cc.kojeve.vielheit.dto.EntryData
import cc.kojeve.vielheit.repository.UserRepository
import cc.kojeve.vielheit.request.EntryRequest
import cc.kojeve.vielheit.util.RestException
import org.springframework.data.repository.CrudRepository

class EntryService(
        override val repository: CrudRepository<Entry, Long>,
        val userRepository: UserRepository
) : Service<Entry, EntryData, EntryRequest>() {

    override fun save(req: EntryRequest): EntryData {
        val user = userRepository.findById(req.userId).orElseThrow { RestException.BadRequest() }

        val entry = Entry(user, req.title)
        repository.save(entry)

        return EntryData(entry)
    }
}