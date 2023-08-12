package org.the_chance.honeymart.domain.model

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
data class LoginEntity(
    val accessToken: String,
    val refreshToken: String,
    )