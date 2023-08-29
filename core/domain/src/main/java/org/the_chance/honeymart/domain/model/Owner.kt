package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.domain.usecase.Tokens

data class Owner(
    val fullName: String,
    val marketId: Long,
    val tokens: Tokens
)
