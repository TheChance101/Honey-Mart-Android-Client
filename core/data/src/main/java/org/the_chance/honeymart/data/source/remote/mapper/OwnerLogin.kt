package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OwnerLoginDto
import org.the_chance.honeymart.data.source.remote.models.TokensDto
import org.the_chance.honeymart.domain.model.OwnerLoginEntity
import org.the_chance.honeymart.domain.model.TokensEntity

fun OwnerLoginDto.toOwnerLoginEntity(): OwnerLoginEntity = OwnerLoginEntity(
    fullName = fullName ?: "",
    marketId = marketId ?: 0L,
    tokens = tokens?.toTokenEntity() ?: TokensEntity("", "")
)

fun TokensDto.toTokenEntity(): TokensEntity = TokensEntity(
    refreshToken = refreshToken ?: "",
    accessToken = accessToken ?: ""
)