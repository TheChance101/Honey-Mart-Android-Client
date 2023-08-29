package org.the_chance.honeymart.ui.feature.notifications

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed interface NotificationsUiEffect: BaseUiEffect{
    object OnClickTryAgain: NotificationsUiEffect
}
