package org.the_chance.honeymart.ui.feature.signup

import org.the_chance.honeymart.util.AuthData

sealed class AuthUiEffect {
    data class ClickSignUpEffect(val authData: AuthData) : AuthUiEffect()
    object ClickContinueEffect : AuthUiEffect()
}