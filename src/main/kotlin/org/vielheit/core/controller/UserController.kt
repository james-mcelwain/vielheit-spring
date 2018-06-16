package org.vielheit.core.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@Controller
class UserController {
    @RequestMapping("/", method = [(RequestMethod.GET)])
    fun index(): String {
        return "Greetings from Spring Boot!"
    }
}