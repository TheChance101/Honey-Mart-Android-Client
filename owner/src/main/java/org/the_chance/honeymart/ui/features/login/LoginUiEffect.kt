package org.the_chance.honeymart.ui.features.login

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed class LoginUiEffect : BaseUiEffect {
    object ClickSignUpEffect : LoginUiEffect()
    object ClickLoginEffect : LoginUiEffect()
    object ShowToastEffect : LoginUiEffect()
}