package org.the_chance.honeymart.ui.signup

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class SignupUiEffect : BaseUiEffect {
    object ClickSignupEffect : SignupUiEffect()
    object ClickLoginEffect : SignupUiEffect()
}