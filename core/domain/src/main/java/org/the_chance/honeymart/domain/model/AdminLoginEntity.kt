package org.the_chance.honeymart.domain.model


data class AdminLoginEntity(
    val accessToken: String,
    val refreshToken: String
)