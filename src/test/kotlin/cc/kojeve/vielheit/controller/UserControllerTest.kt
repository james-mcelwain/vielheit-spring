package cc.kojeve.vielheit.controller

import cc.kojeve.vielheit.TestCase
import cc.kojeve.vielheit.request.RegistrationRequest
import cc.kojeve.vielheit.service.UserService
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
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
        mockMvc.perform(get("/user"))
                .andExpect(status().`is`(403))

        val user = userService.save(RegistrationRequest("Bob", "pass"))

        val token = mockMvc.perform(MockMvcRequestBuilders.post("/user/auth")
                .content(""" { "username": "Bob", "password": "pass" } """)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(::println)
                .andExpect(status().`is`(200))
                .andReturn().response.contentAsString

        mockMvc.perform(get("/user")
                .header("Authorization", "Bearer $token"))
                .andExpect(status().`is`(200))
                .andExpect(content().string(containsString(user.username)))
    }
}