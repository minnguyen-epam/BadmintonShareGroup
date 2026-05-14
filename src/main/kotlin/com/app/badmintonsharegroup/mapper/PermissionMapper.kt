package com.app.badmintonsharegroup.mapper

import com.app.badmintonsharegroup.domain.entity.Permission
import com.app.badmintonsharegroup.domain.model.PermissionModel
import com.app.badmintonsharegroup.dto.response.PermissionResponse
import org.mapstruct.Mapper

@Mapper
interface PermissionMapper {

    // Entity <-> Model
    fun toModel(entity: Permission): PermissionModel

    fun toEntity(model: PermissionModel): Permission

    // Model -> Response
    fun toResponse(model: PermissionModel): PermissionResponse
}
