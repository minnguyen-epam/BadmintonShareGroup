package com.app.badmintonsharegroup.mapper

import com.app.badmintonsharegroup.controller.dto.CreateUserAccountRequest
import com.app.badmintonsharegroup.domain.entity.UserAccount
import com.app.badmintonsharegroup.domain.model.UserAccountModel
import com.app.badmintonsharegroup.dto.response.UserAccountResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface UserAccountMapper {

    // Entity <-> Model
    fun toModel(entity: UserAccount): UserAccountModel

    fun toEntity(model: UserAccountModel): UserAccount

    // Model -> Response
    fun toResponse(model: UserAccountModel): UserAccountResponse

    // Request -> Model
    @Mapping(target = "id", ignore = true)
    fun toModel(request: CreateUserAccountRequest): UserAccountModel
}
