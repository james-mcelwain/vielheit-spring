package cc.kojeve.vielheit.controller

import cc.kojeve.vielheit.domain.User
import cc.kojeve.vielheit.service.UserService
import cc.kojeve.vielheit.util.RestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): Any {
        val user = userService.findById(id) ?: throw RestException.NotFound()
        return user
    }
}