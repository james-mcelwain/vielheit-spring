package cc.kojeve.vielheit.config.security

import cc.kojeve.vielheit.TestCase
import cc.kojeve.vielheit.dto.RegistrationData
import cc.kojeve.vielheit.repository.UserRepository
import cc.kojeve.vielheit.service.UserService
import org.hamcrest.CoreMatchers.containsString

import org.junit.Assert.*
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class WebSecurityConfigTest : TestCase() {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var userService: UserService

    @Test
    fun auth() {
        val user = userService.save(RegistrationData("asdf", "asdf"))

        mockMvc.perform(post("/user/auth")
                .content(""" { "username": "asdf", "password": "asdff" } """)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(::println)
                .andExpect(status().`is`(403))

        val token = mockMvc.perform(post("/user/auth")
                .content(""" { "username": "asdf", "password": "asdf" } """)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(::println)
                .andExpect(status().`is`(200))
                .andReturn().response.contentAsString

        mockMvc.perform(get("/user/${user.id}")
                .header("Authorization", "Bearer $token"))
                .andExpect(status().`is`(200))
                .andExpect(content().string(containsString(user.username)))
    }
}