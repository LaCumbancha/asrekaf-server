package com.fiuba.asrekaf.service

import com.fiuba.asrekaf.api.UserApiKey
import com.fiuba.asrekaf.model.User
import com.fiuba.asrekaf.api.UserCreation
import com.fiuba.asrekaf.api.UserLogin
import com.fiuba.asrekaf.repository.UserRepository
import com.fiuba.asrekaf.utils.HashType.*
import com.fiuba.asrekaf.utils.Hasher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun createUser(userData: UserCreation) =
        userRepository.save(userData.toUserEntity()).let { UserApiKey(it.apiKey) }

    private fun UserCreation.toUserEntity() = User(
        username = username,
        password = Hasher.hash(password, SHA256),
        apiKey = generateApiKey()
    )

    fun login(userId: Long, userData: UserLogin): ResponseEntity<User> =
        userRepository.findById(userId)
            .filter { Hasher.verify(userData.password, it.password, SHA256) && generateToken(it.apiKey) == userData.token }
            .map { ResponseEntity.ok().body(it) }
            .orElse(ResponseEntity.notFound().build())

    companion object {
        private fun generateToken(tokenKey: String): String = ""

        private fun generateApiKey(): String = ThreadLocalRandom.current()
            .ints(STRING_LENGTH, 0, charPool.size)
            .asSequence()
            .map(charPool::get)
            .joinToString("")

        private const val STRING_LENGTH = 40L
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    }

}