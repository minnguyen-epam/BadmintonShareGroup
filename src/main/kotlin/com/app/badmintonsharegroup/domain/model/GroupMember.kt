package com.app.badmintonsharegroup.domain.model

import java.util.UUID

data class GroupMemberModel(
    val id: UUID?,
    val groupName: String,
    val typeOfGroup: String,
    val groupCategory: String,
    val ownerId: UUID,
)
