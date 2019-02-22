package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.domain.Domain
import cc.kojeve.vielheit.dto.Dto
import cc.kojeve.vielheit.request.Request
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional

@Transactional
abstract class Service<DomainT: Domain, DtoT: Dto<DomainT>, SaveReqT: Request> {
    abstract protected val repository: CrudRepository<DomainT, Long>
    abstract fun save(req: SaveReqT): DtoT
}