package cc.kojeve.vielheit.dto

import cc.kojeve.vielheit.domain.Domain

abstract class Dto<DomainT : Domain>(domain: DomainT) {
    val id: Long = domain.id!!
}