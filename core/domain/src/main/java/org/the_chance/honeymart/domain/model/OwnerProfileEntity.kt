package org.the_chance.honeymart.domain.model

data class OwnerProfileEntity(
    val userId: Int,
    val fullName: String,
    val email: String,
    val profileImage: String,
)
