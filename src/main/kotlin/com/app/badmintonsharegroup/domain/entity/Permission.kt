package com.app.badmintonsharegroup.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("permission")
data class Permission(
    @Id
    val id: UUID? = null,

    val role: Role,

    val description: String? = null,
)

enum class Role {
    GLOBAL_ADMIN,
    GROUP_ADMIN,
    USER,
}
