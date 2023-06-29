package org.the_chance.honeymart.ui.feature.ui_effect

import org.the_chance.honeymart.util.AuthData

sealed class AuthenticationUiEffect : BaseUiEffect(){
    data class ClickSignUpEffect(val authData: AuthData) : AuthenticationUiEffect()
    data class ClickLoginEffect(val authData: AuthData) : AuthenticationUiEffect()
}