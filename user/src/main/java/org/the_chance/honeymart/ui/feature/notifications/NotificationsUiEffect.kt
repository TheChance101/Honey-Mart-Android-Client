package org.the_chance.honeymart.ui.feature.notifications

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class NotificationsUiEffect: BaseUiEffect{
    object OnClickTryAgain: NotificationsUiEffect()
}
