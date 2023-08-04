package org.the_chance.honeymart.ui.feature.authentication

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class AuthenticationUiEffect : BaseUiEffect{
    object ClickSignUpEffect : AuthenticationUiEffect()
    object ClickLoginEffect : AuthenticationUiEffect()
}