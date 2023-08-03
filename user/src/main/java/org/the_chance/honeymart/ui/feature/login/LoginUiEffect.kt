package org.the_chance.honeymart.ui.feature.login



sealed class LoginUiEffect {
    object ClickSignUpEffect : LoginUiEffect()
    object ClickLoginEffect : LoginUiEffect()
    object ShowToastEffect : LoginUiEffect()
}