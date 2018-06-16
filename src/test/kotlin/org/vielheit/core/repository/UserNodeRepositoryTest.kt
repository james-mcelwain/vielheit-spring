package org.vielheit.core.repository

import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.vielheit.core.BaseTest
import org.vielheit.core.domain.User
import org.vielheit.core.domain.UserNode

class UserNodeRepositoryTest : BaseTest() {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userNodeRepository: UserNodeRepository

    @Test
    fun findById() {
        val user = User(firstName = "test", lastName = "user", email = "test@user.com", password = "")
        userRepository.save(user)
        val userNode = UserNode()
        userNode.userId = user.id
        userNodeRepository.save(userNode)
        val foundUser = userNodeRepository.findByUserId(user.id!!)
        Assert.assertEquals(user.id!!, foundUser!!.userId)
    }
}