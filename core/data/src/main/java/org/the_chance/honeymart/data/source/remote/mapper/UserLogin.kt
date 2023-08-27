package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.UserLoginDto
import org.the_chance.honeymart.domain.model.OwnerFields

fun UserLoginDto.toUserLoginFields(): OwnerFields.TokensFields =
    OwnerFields.TokensFields(
        accessToken = this.accessToken ?: "",
        refreshToken = this.refreshToken ?: ""
    )