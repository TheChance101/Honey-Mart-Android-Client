package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OwnerLoginDto
import org.the_chance.honeymart.data.source.remote.models.TokensDto
import org.the_chance.honeymart.domain.model.OwnerFields

fun OwnerLoginDto.toOwnerFields(): OwnerFields = OwnerFields(
    fullName = fullName ?: "",
    marketId = marketId ?: 0L,
    tokens = tokens?.toTokenFields() ?: OwnerFields.TokensFields("", "")
)

fun TokensDto.toTokenFields(): OwnerFields.TokensFields = OwnerFields.TokensFields(
    refreshToken = refreshToken ?: "",
    accessToken = accessToken ?: ""
)