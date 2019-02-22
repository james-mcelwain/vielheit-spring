package cc.kojeve.vielheit.controller

import cc.kojeve.vielheit.TestCase
import cc.kojeve.vielheit.request.RegistrationRequest
import cc.kojeve.vielheit.service.UserService
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserControllerTest : TestCase() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userService: UserService

    @Test
    fun getUser() {
        mockMvc.perform(get("/user/123456789"))
                .andExpect(status().`is`(404))

        val user = userService.findById(1) ?: userService.save(RegistrationRequest("Bob", ""))

        mockMvc.perform(get("/user/1"))
                .andExpect(status().`is`(200))
                .andExpect(content().string(containsString(user.username)))
    }
}