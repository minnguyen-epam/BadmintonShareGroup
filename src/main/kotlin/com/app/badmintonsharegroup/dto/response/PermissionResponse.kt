package com.app.badmintonsharegroup.dto.response

import com.app.badmintonsharegroup.domain.entity.Role
import java.util.UUID

data class PermissionResponse(val id: UUID?, val role: Role, val description: String?)
