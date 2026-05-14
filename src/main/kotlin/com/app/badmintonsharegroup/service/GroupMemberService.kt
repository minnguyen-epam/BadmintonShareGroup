package com.app.badmintonsharegroup.service

import com.app.badmintonsharegroup.controller.dto.CreateGroupMemberRequest
import com.app.badmintonsharegroup.domain.model.GroupMemberModel
import com.app.badmintonsharegroup.mapper.GroupMemberMapper
import com.app.badmintonsharegroup.repository.GroupMemberRepository
import com.app.badmintonsharegroup.repository.UserAccountRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class GroupMemberService(
    private val groupMemberRepository: GroupMemberRepository,
    private val userAccountRepository: UserAccountRepository,
    private val groupMemberMapper: GroupMemberMapper,
) {

    fun getAllGroupMembers(): Flux<GroupMemberModel> {
        return groupMemberRepository.findAll()
            .map { groupMemberMapper.toModel(it) }
    }

    @Transactional
    fun createGroupMember(request: CreateGroupMemberRequest): Mono<GroupMemberModel> {
        return userAccountRepository.existsById(request.ownerId)
            .flatMap { ownerExists ->
                if (!ownerExists) {
                    Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found: ${request.ownerId}"))
                } else {
                    groupMemberRepository.existsByGroupNameAndOwnerId(request.groupName, request.ownerId)
                }
            }
            .flatMap { duplicated ->
                if (duplicated) {
                    Mono.error(
                        ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Group '${request.groupName}' already exists for this owner"
                        )
                    )
                } else {
                    groupMemberRepository.save(groupMemberMapper.toEntity(groupMemberMapper.toModel(request)))
                }
            }
            .map { groupMemberMapper.toModel(it) }
    }
}