package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.UserLoginDto
import org.the_chance.honeymart.domain.model.UserLoginEntity

fun UserLoginDto.toUserLoginEntity(): UserLoginEntity =
    UserLoginEntity(
        accessToken = this.accessToken ?: "",
        refreshToken = this.refreshToken ?: ""
    )