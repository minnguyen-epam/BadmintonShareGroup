package com.app.badmintonsharegroup.repository

import com.app.badmintonsharegroup.domain.entity.GroupMember
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface GroupMemberRepository : ReactiveCrudRepository<GroupMember, UUID> {

    fun findAllByOwnerId(ownerId: UUID): Flux<GroupMember>

    fun findByGroupNameAndOwnerId(groupName: String, ownerId: UUID): Mono<GroupMember>

    fun existsByGroupNameAndOwnerId(groupName: String, ownerId: UUID): Mono<Boolean>
}
