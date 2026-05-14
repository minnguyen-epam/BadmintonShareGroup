package com.app.badmintonsharegroup.controller

import com.app.badmintonsharegroup.controller.dto.CreateGroupMemberRequest
import com.app.badmintonsharegroup.domain.model.GroupMemberModel
import com.app.badmintonsharegroup.service.GroupMemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/group-members")
class GroupMemberController(
    private val groupMemberService: GroupMemberService
) {
    @GetMapping
    fun getAllGroupMembers(): Flux<GroupMemberModel> = groupMemberService.getAllGroupMembers()

    @PostMapping
    fun createGroupMember(request: CreateGroupMemberRequest): Mono<GroupMemberModel> {
        return groupMemberService.createGroupMember(request)
    }
}