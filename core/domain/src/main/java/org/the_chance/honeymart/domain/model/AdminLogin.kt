package org.the_chance.honeymart.domain.model

data class AdminLogin(
    val accessToken: String,
    val refreshToken: String
)
