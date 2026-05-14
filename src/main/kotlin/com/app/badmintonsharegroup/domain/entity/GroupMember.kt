package com.app.badmintonsharegroup.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("group_member")
data class GroupMember(
    @Id
    val id: UUID? = null,

    @Column("group_name")
    val groupName: String,

    @Column("type_of_group")
    val typeOfGroup: String,

    @Column("group_category")
    val groupCategory: String,

    @Column("owner_id")
    val ownerId: UUID,

    @CreatedDate
    @Column("created_at")
    var createdAt: Instant? = null,

    @LastModifiedDate
    @Column("updated_at")
    var updatedAt: Instant? = null,
)
