package org.the_chance.honeymart.domain.model

data class OwnerLoginEntity(
    val fullName: String,
    val marketId: Long,
    val tokens: TokensEntity
)

data class TokensEntity(
    val accessToken: String,
    val refreshToken: String
)