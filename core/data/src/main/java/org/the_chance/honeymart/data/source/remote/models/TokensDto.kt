package org.the_chance.honeymart.data.source.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class TokensDto(
    val accessToken: String?,
    val refreshToken: String?
)