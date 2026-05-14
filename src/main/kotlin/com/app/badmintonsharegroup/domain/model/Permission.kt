package com.app.badmintonsharegroup.domain.model

import com.app.badmintonsharegroup.domain.entity.Role
import java.util.UUID

data class PermissionModel(val id: UUID?, val role: Role, val description: String?)
