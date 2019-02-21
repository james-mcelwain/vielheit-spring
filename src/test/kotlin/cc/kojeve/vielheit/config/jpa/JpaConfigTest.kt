package cc.kojeve.vielheit.config.jpa

import cc.kojeve.vielheit.TestCase
import cc.kojeve.vielheit.domain.User
import cc.kojeve.vielheit.repository.UserRepository

import org.junit.Assert.*
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class JpaConfigTest : TestCase() {
    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun audit() {
        val user = userRepository.save(User("", "", emptyList()))

        assertNotNull(user.createDate)
        assertNotNull(user.updateDate)
    }
}