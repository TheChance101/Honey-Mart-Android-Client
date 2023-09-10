package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.AdminLoginDto
import org.the_chance.honeymart.domain.model.AdminLogin
import org.the_chance.honeymart.domain.usecase.Tokens

fun AdminLoginDto.toAdminLogin(): AdminLogin = AdminLogin(
    tokens = tokens?.toTokens() ?: Tokens("", "") ,
    name = name ?: ""
)