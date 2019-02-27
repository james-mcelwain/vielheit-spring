package cc.kojeve.vielheit.repository

import cc.kojeve.vielheit.TestCase
import cc.kojeve.vielheit.domain.Entry
import cc.kojeve.vielheit.domain.Tag
import cc.kojeve.vielheit.domain.User
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class EntryRepositoryTest: TestCase() {
    @Autowired
    lateinit var entryRepository: EntryRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var tagRepository: TagRepository

    @Test
    fun `find entries by user`() {
        val user = userRepository.save(User("1", ""))
        user.entries.add(Entry(user, "entry"))

        var entries = entryRepository.findByUser(user)
        assertThat(entries.size, `is`(equalTo(1)))

        user.entries.add(Entry(user, "entry2"))
        entries = entryRepository.findByUser(user)
        assertThat(entries.size, `is`(equalTo(2)))

        val user2 = userRepository.save(User("2", ""))
        user2.entries.add(Entry(user2, "2entry1"))
        user2.entries.add(Entry(user2, "2entry2"))
        user2.entries.add(Entry(user2, "2entry3"))

        entries = entryRepository.findByUser(user)
        assertThat(entries.size, `is`(equalTo(2)))
    }

    @Test
    fun `find by user and tag`() {
        val user = userRepository.save(User("1", ""))
        val entry = Entry(user, "entry")
        val tag = Tag(user, "tag")
        entry.tags.add(tag)
        tag.entries.add(entry)
        user.entries.add(entry)

        val entries = entryRepository.findByUserAndTags(user, tag)
        assertThat(entries.size, `is`(equalTo(1)))
        assertThat(entries.first().title, `is`(equalTo(entry.title)))
    }
}