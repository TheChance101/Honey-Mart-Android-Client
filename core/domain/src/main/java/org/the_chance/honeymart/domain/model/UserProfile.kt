package org.the_chance.honeymart.domain.model

data class UserProfile(
    val userId: Long,
    val fullName: String,
    val email: String,
    val profileImage: String,
)

