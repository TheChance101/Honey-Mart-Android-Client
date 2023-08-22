package org.the_chance.honeymart.domain.model

data class OwnerProfileEntity(
    val ownerId: Long,
    val fullName: String,
    val email: String,
    val imageUrl: String,
)
