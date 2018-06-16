package org.vielheit.core.repository

import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.vielheit.core.BaseTest
import org.vielheit.core.domain.User

class UserRepositoryTest : BaseTest() {
    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun findByEmail() {
        val user = User(active = true, firstName = "test", lastName = "user", email = "test@user.com", password = "")
        userRepository.save(user)
        val foundUser = userRepository.findByEmail("test@user.com")!!
        Assert.assertEquals("test@user.com", foundUser.email)
        Assert.assertEquals("test", foundUser.firstName)
        Assert.assertEquals("user",  foundUser.lastName)
    }
}