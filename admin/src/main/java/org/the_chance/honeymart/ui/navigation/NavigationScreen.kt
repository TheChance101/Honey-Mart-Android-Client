package org.the_chance.honeymart.ui.navigation

import org.the_chance.design_system.R

sealed class NavigationScreen(
    val route: String,
    val label: String,
    val selectedIcon: Int,
) {
    object Requests : NavigationScreen(
        route = Screen.Requests.route,
        label = "Requests",
        selectedIcon = R.drawable.icon_market_nav,
    )

}