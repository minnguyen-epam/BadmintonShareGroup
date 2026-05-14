package com.app.badmintonsharegroup.dto.response

import java.util.UUID

data class UserAccountResponse(
    val id: UUID?,
    val name: String,
    val username: String,
    val email: String,
    val phoneNumber: String?,
)
