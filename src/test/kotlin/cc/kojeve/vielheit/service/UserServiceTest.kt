package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.TestCase
import cc.kojeve.vielheit.request.RegistrationRequest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test

import org.junit.Assert.*
import org.springframework.beans.factory.annotation.Autowired

class UserServiceTest : TestCase() {
    @Autowired
    private lateinit var userService: UserService

    @Test
    fun save() {

    }

    @Test
    fun findByUserId() {
        val beep = userService.save(RegistrationRequest("findByUserId", ""))

        assertNotNull("User was not found.", userService.findById(beep.id))
        assertThat("User has wrong name.", userService.findById(beep.id)!!.username, `is`(equalTo(beep.username)))
    }
}