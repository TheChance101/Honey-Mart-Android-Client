package org.the_chance.honeymart.domain.model

data class OwnerFields(
    val fullName: String,
    val marketId: Long,
    val tokens: TokensFields
) {
    data class TokensFields(
        val accessToken: String,
        val refreshToken: String
    )
}

