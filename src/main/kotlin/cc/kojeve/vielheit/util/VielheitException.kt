package cc.kojeve.vielheit.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class VielheitException(override val message: String) : RuntimeException()