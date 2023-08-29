package org.the_chance.honeymart.ui.navigation

import org.the_chance.design_system.R

sealed class NavigationRailScreen(
    val route: String,
    val label: String,
    val selectedIcon: Int,
) {
    object Orders : NavigationRailScreen(
        route = Screen.Orders.route,
        label = "Orders",
        selectedIcon = R.drawable.ic_orders,
    )

    object Category : NavigationRailScreen(
        route = Screen.Category.route,
        label = "Category",
        selectedIcon = R.drawable.ic_category,
    )

    object Coupons : NavigationRailScreen(
        route = Screen.Coupons.route,
        label = "Coupons",
        selectedIcon = R.drawable.ic_coupon,
    )
}