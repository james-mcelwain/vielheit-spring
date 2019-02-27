package cc.kojeve.vielheit.response

import cc.kojeve.vielheit.dto.UserData

class AuthResponse(val token: String, val user: UserData)