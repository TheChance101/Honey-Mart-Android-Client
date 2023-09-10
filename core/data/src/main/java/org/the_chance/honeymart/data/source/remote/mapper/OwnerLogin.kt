package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OwnerLoginDto
import org.the_chance.honeymart.data.source.remote.models.TokensDto
import org.the_chance.honeymart.domain.model.Owner
import org.the_chance.honeymart.domain.usecase.Tokens

fun OwnerLoginDto.toOwnerFields(): Owner = Owner(
    fullName = fullName ?: "",
    marketId = marketId ?: 0L,
    tokens = tokens?.toTokens() ?: Tokens("", "")
)