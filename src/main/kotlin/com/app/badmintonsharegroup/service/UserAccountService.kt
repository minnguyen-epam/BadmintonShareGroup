package com.app.badmintonsharegroup.service

import com.app.badmintonsharegroup.controller.dto.AuthResponse
import com.app.badmintonsharegroup.controller.dto.CreateUserAccountRequest
import com.app.badmintonsharegroup.controller.dto.LoginRequest
import com.app.badmintonsharegroup.dto.response.UserAccountResponse
import com.app.badmintonsharegroup.mapper.UserAccountMapper
import com.app.badmintonsharegroup.repository.UserAccountRepository
import com.app.badmintonsharegroup.security.JwtService
import org.springframework.http.HttpStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Service
class UserAccountService(
    private val userAccountRepository: UserAccountRepository,
    private val userAccountMapper: UserAccountMapper,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> = userAccountRepository.findByUsername(username)
        .map { account ->
            User.builder()
                .username(account.username)
                .password(account.password)
                .authorities(SimpleGrantedAuthority("ROLE_USER"))
                .build()
        }
        .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: $username")))

    @Transactional
    fun register(request: CreateUserAccountRequest): Mono<UserAccountResponse> =
        userAccountRepository.existsByUsername(request.username)
            .flatMap { usernameExists ->
                if (usernameExists) {
                    Mono.error(ResponseStatusException(HttpStatus.CONFLICT, "Username already taken"))
                } else {
                    userAccountRepository.existsByEmail(request.email)
                }
            }
            .flatMap { emailExists ->
                if (emailExists) {
                    Mono.error(ResponseStatusException(HttpStatus.CONFLICT, "Email already registered"))
                } else {
                    val model = userAccountMapper.toModel(request)
                        .copy(password = passwordEncoder.encode(request.password)!!)
                    userAccountRepository.save(userAccountMapper.toEntity(model))
                }
            }
            .map { userAccountMapper.toResponse(userAccountMapper.toModel(it)) }

    @Transactional
    fun login(request: LoginRequest): Mono<AuthResponse> = userAccountRepository.findByUsername(request.username)
        .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")))
        .flatMap { account ->
            if (!passwordEncoder.matches(request.password, account.password)) {
                Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"))
            } else {
                val token = jwtService.generateToken(account.username)
                Mono.just(AuthResponse(token = token, userId = account.id, username = account.username))
            }
        }
}
