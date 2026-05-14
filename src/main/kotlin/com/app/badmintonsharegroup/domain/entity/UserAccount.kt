package com.app.badmintonsharegroup.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("user_account")
data class UserAccount(
    @Id
    val id: UUID? = null,

    val name: String,

    val username: String,

    val email: String,

    @Column("phone_number")
    val phoneNumber: String? = null,

    val password: String,

    @CreatedDate
    @Column("created_at")
    var createdAt: Instant? = null,

    @LastModifiedDate
    @Column("updated_at")
    var updatedAt: Instant? = null,
)
