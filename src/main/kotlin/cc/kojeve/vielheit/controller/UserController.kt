package cc.kojeve.vielheit.controller

import cc.kojeve.vielheit.request.AuthRequest
import cc.kojeve.vielheit.response.AuthResponse
import cc.kojeve.vielheit.service.UserService
import cc.kojeve.vielheit.util.RestException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @PostMapping("/auth")
    fun auth(@RequestBody authRequest: AuthRequest): AuthResponse {
        val token = userService.auth(authRequest.username, authRequest.password)
        val user = userService.findByUsername(authRequest.username)!!
        return AuthResponse(token, user)
    }

    @GetMapping
    fun getUser(): Any {
        return userService.getUser()
    }
}