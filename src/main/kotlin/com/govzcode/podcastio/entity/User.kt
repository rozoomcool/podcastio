package com.govzcode.podcastio.entity

import jakarta.persistence.*

@Entity
@Table(name="users")
class User(
    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(value = EnumType.STRING)
    var role: Role
): BaseAuditEntity<Long>()