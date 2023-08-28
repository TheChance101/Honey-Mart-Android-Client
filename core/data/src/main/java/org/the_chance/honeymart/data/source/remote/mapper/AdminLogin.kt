package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.AdminLoginDto
import org.the_chance.honeymart.domain.model.AdminLogin

fun AdminLoginDto.toAdminLogin(): AdminLogin = AdminLogin(
    refreshToken = refreshToken ?: "",
    accessToken = accessToken ?: ""
)