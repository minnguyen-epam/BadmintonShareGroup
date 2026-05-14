package com.app.badmintonsharegroup.mapper

import com.app.badmintonsharegroup.controller.dto.CreateGroupMemberRequest
import com.app.badmintonsharegroup.domain.entity.GroupMember
import com.app.badmintonsharegroup.domain.model.GroupMemberModel
import com.app.badmintonsharegroup.dto.response.GroupMemberResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface GroupMemberMapper {

    // Entity <-> Model
    fun toModel(entity: GroupMember): GroupMemberModel

    fun toEntity(model: GroupMemberModel): GroupMember

    // Model -> Response
    fun toResponse(model: GroupMemberModel): GroupMemberResponse

    // Request -> Model
    @Mapping(target = "id", ignore = true)
    fun toModel(request: CreateGroupMemberRequest): GroupMemberModel
}
