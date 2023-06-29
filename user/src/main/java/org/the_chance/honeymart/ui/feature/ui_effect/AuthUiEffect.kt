package org.the_chance.honeymart.ui.feature.ui_effect

import org.the_chance.honeymart.util.AuthData

sealed class AuthUiEffect : BaseUiEffect(){
    data class ClickSignUpEffect(val authData: AuthData) : AuthUiEffect()
    object ClickContinueEffect : AuthUiEffect()
}