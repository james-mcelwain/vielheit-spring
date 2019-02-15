package cc.kojeve.vielheit.dto

import cc.kojeve.vielheit.domain.User

class UserData(
        val id: Long,
        val username: String
) {
    constructor(user: User) : this(
            id = user.id!!,
            username = user.username
    )
}