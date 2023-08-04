package org.the_chance.honeymart.ui.feature.signup

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class SignupUiEffect: BaseUiEffect {
    object ClickSignupEffect : SignupUiEffect()
    object ShowToastEffect : SignupUiEffect()

    object ClickLoginEffect : SignupUiEffect()



}