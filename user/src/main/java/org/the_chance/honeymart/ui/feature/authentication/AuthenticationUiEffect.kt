package org.the_chance.honeymart.ui.feature.authentication

import org.the_chance.honeymart.util.AuthData

sealed class AuthenticationUiEffect {
    data class ClickSignUpEffect(val authData: AuthData) : AuthenticationUiEffect()
    data class ClickLoginEffect(val authData: AuthData) : AuthenticationUiEffect()
}