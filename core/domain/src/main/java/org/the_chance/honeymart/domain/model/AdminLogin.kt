package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.domain.usecase.Tokens

data class AdminLogin(
    val tokens: Tokens,
    val name: String
)
