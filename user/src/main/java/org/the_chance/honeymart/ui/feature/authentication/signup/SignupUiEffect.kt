package org.the_chance.honeymart.ui.feature.authentication.signup

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed interface SignupUiEffect: BaseUiEffect {
    object ClickSignupEffect : SignupUiEffect
    object ShowToastEffect : SignupUiEffect
    object ClickLoginEffect : SignupUiEffect

}