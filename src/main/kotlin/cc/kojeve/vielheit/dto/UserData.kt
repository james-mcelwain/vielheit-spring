package cc.kojeve.vielheit.dto

import cc.kojeve.vielheit.domain.User

class UserData(user: User) : Dto<User>(user) {
    val username: String = user.username
}