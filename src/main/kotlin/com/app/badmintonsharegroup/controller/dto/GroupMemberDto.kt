package com.app.badmintonsharegroup.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class CreateGroupMemberRequest(
    @field:NotBlank
    val groupName: String,

    @field:NotBlank
    val typeOfGroup: String,

    @field:NotBlank
    val groupCategory: String,

    @field:NotNull
    val ownerId: UUID,
)

data class UpdateGroupMemberRequest(
    @field:Size(max = 100)
    val groupName: String?,

    val typeOfGroup: String?,

    val groupCategory: String?,
)
