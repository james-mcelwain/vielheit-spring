package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.domain.Domain
import org.springframework.data.repository.CrudRepository

abstract class Service<T: Domain> {
    abstract protected val repository: CrudRepository<T, Long>
}