package org.the_chance.honeymart.ui.navigation

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class NavigationRailEffect : BaseUiEffect {
    object OnClickProfileImage: NavigationRailEffect()
    object OnClickProfileName: NavigationRailEffect()
}