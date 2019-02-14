package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.TestCase
import cc.kojeve.vielheit.domain.User
import cc.kojeve.vielheit.util.VielheitException
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
        var bob = User("Bob")
        bob = userService.save(bob)

        assertNotNull("User was not saved.", bob.id)

        try {
            userService.save(bob)
            fail("Saving existing user")
        } catch(ex: VielheitException) { }
    }

    @Test
    fun findByUserId() {
        val beep = userService.save(User("Beep"))

        assertNotNull("User was not found.", userService.findById(beep.id!!))
        assertThat("User has wrong name.", userService.findById(beep.id!!)!!.firstName, `is`(equalTo("Beep")))
    }
}