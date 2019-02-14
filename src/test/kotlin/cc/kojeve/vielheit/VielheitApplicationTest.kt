package cc.kojeve.vielheit

import cc.kojeve.vielheit.repository.UserRepository
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class VielheitApplicationTest : TestCase() {
	@Autowired
	private lateinit var userRepository: UserRepository

	@Test
	fun contextLoads() {
	}
}