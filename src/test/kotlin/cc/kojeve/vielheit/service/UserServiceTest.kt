package cc.kojeve.vielheit.service

import cc.kojeve.vielheit.TestCase
import cc.kojeve.vielheit.domain.User
import cc.kojeve.vielheit.util.VielheitException
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentMatchers.isNotNull
import org.mockito.ArgumentMatchers.notNull
import org.springframework.beans.factory.annotation.Autowired

class UserServiceTest : TestCase() {
    @Autowired
    private lateinit var userService: UserService

    @Test
    fun save() {
        var user = User("Bob")
        user = userService.save(user)

        assertNotNull("User was not saved.", user.id)

        try {
            userService.save(user)
            fail("Saving existing user")
        } catch(ex: VielheitException) {

        }
    }
}