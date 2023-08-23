package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.AdminLoginDto
import org.the_chance.honeymart.domain.model.AdminLoginEntity

fun AdminLoginDto.toAdminLoginEntity(): AdminLoginEntity = AdminLoginEntity(
    refreshToken = refreshToken ?: "",
    accessToken = accessToken ?: ""
)