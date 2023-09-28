package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.UserLoginDto
import org.the_chance.honeymart.domain.usecase.Tokens

fun UserLoginDto.toUserLoginFields(): Tokens =
    Tokens(
        accessToken = this.accessToken ?: "",
        refreshToken = this.refreshToken ?: ""
    )