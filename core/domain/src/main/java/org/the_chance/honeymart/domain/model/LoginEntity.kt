package org.the_chance.honeymart.domain.model

data class UserLoginEntity(
    val refreshToken: String,
    val accessToken: String,
)