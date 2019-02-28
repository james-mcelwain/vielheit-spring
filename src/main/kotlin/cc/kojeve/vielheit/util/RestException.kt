package cc.kojeve.vielheit.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class RestException : RuntimeException() {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class NotFound : RestException()

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    class Unauthorized : RestException()

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class BadRequest : RestException()

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class InternalServeError : RestException()
}