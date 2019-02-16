package cc.kojeve.vielheit.controller

import cc.kojeve.vielheit.dto.AuthRequest
import cc.kojeve.vielheit.service.UserService
import cc.kojeve.vielheit.util.RestException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @PostMapping("/auth")
    fun auth(@RequestBody authRequest: AuthRequest): String {
        return userService.auth(authRequest.username, authRequest.password)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): Any {
        val user = userService.findById(id) ?: throw RestException.NotFound()
        return user
    }
}