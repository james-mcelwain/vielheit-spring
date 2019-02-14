package cc.kojeve.vielheit.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

sealed class RestException: RuntimeException() {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class NotFound: RestException()
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    class Unauthorized: RestException()
}