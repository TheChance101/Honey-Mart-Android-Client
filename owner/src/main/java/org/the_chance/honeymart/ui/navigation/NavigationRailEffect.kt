package org.the_chance.honeymart.ui.navigation

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class NavigationRailEffect : BaseUiEffect {
    object OnClickProfileEffect : NavigationRailEffect()
    object OnClickLogoutEffect : NavigationRailEffect()

}