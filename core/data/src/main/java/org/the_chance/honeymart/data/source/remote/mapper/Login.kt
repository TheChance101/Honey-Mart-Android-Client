package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.LoginDto
import org.the_chance.honeymart.domain.model.LoginEntity

internal fun LoginDto.toLoginEntity() = LoginEntity(
    accessToken = accessToken ?: "",
    refreshToken = refreshToken ?: "",

    )