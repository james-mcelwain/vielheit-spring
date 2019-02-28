package cc.kojeve.vielheit.controller

import cc.kojeve.vielheit.request.AuthRequest
import cc.kojeve.vielheit.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @PostMapping("/auth")
    fun auth(@RequestBody authRequest: AuthRequest): String {
        return userService.auth(authRequest.username, authRequest.password)
    }

    @GetMapping
    fun getUser(): Any {
        return userService.getUser()
    }
}