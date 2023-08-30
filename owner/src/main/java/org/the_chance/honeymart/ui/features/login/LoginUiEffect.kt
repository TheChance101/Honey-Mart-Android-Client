package org.the_chance.honeymart.ui.features.login

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed class LoginUiEffect : BaseUiEffect {
    object ClickSignUpEffect : LoginUiEffect()
    object NavigateToCategoriesEffect : LoginUiEffect()
    object ShowLoginErrorToastEffect : LoginUiEffect()
    object NavigateToWaitingApproveEffect: LoginUiEffect()
    object NavigateToCreateMarketEffect : LoginUiEffect()
    object NavigateToLoginEffect : LoginUiEffect()
}