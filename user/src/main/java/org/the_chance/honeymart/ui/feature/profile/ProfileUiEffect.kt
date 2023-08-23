package org.the_chance.honeymart.ui.feature.profile

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class ProfileUiEffect: BaseUiEffect {
    object ClickMyOrderEffect : ProfileUiEffect()
    object ClickCouponsEffect : ProfileUiEffect()
    object ClickNotificationEffect : ProfileUiEffect()
    object ClickThemeEffect : ProfileUiEffect()
    object ClickLogoutEffect : ProfileUiEffect()

    object ShowToastEffect : ProfileUiEffect()
    object ShowDialogEffect : ProfileUiEffect()
}