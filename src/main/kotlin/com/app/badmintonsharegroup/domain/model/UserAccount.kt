package com.app.badmintonsharegroup.domain.model

import java.util.UUID

data class UserAccountModel(
    val id: UUID?,
    val name: String,
    val username: String,
    val email: String,
    val phoneNumber: String?,
    val password: String,
)
