package org.the_chance.honeymart.ui.feature.authentication

sealed class AuthenticationUiEffect {
    object ClickSignUpEffect : AuthenticationUiEffect()
    object ClickLoginEffect : AuthenticationUiEffect()
}