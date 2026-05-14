package com.app.badmintonsharegroup.dto.response

import java.time.Instant
import java.util.UUID

data class GroupMemberResponse(
    val id: UUID?,
    val groupName: String,
    val typeOfGroup: String,
    val groupCategory: String,
    val ownerId: UUID,
)
