package com.app.badmintonsharegroup.repository

import com.app.badmintonsharegroup.domain.entity.Permission
import com.app.badmintonsharegroup.domain.entity.Role
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface PermissionRepository : ReactiveCrudRepository<Permission, UUID> {

    fun findByRole(role: Role): Mono<Permission>
}
