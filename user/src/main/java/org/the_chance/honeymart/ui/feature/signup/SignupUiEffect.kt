package org.the_chance.honeymart.ui.feature.signup

sealed class SignupUiEffect {
    object ClickSignupEffect : SignupUiEffect()
    object ShowToastEffect : SignupUiEffect()

    object ClickLoginEffect : SignupUiEffect()



}