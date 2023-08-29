package org.the_chance.honeymart.ui.navigation

import org.the_chance.design_system.R

sealed class NavigationRailScreen(
    val route: String,
    val label: String,
    val selectedIcon: Int,
) {
    object Markets : NavigationRailScreen(
        route = Screen.MARKETS.route,
        label = "Markets",
        selectedIcon = R.drawable.icon_market_nav,
    )
}

