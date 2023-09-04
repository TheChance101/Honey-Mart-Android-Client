package org.the_chance.honeymart.ui.feature.authentication.login

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed interface LoginUiEffect: BaseUiEffect {
    object ClickSignUpEffect : LoginUiEffect
    object ClickLoginEffect : LoginUiEffect
    object ShowToastEffect : LoginUiEffect
}