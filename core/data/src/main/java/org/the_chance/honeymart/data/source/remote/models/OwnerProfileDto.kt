package org.the_chance.honeymart.data.source.remote.models

data class OwnerProfileDto(
    val userId: Int,
    val fullName: String,
    val email: String,
    val profileImage: String,
)
