package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.TokensDto
import org.the_chance.honeymart.domain.usecase.Tokens

fun TokensDto.toTokens() = Tokens(
    accessToken = accessToken ?: "",
    refreshToken = refreshToken ?: ""
)