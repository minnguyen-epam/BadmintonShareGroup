package com.app.badmintonsharegroup.repository

import com.app.badmintonsharegroup.domain.entity.UserAccount
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface UserAccountRepository : ReactiveCrudRepository<UserAccount, UUID> {

    fun findByUsername(username: String): Mono<UserAccount>

    fun findByEmail(email: String): Mono<UserAccount>

    fun existsByUsername(username: String): Mono<Boolean>

    fun existsByEmail(email: String): Mono<Boolean>
}
