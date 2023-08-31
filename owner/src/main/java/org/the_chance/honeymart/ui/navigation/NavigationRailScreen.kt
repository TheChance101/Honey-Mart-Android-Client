package org.the_chance.honeymart.ui.navigation

import org.the_chance.design_system.R

sealed class NavigationRailScreen(
    val route: String,
    val label: Int,
    val selectedIcon: Int,
) {
    object Orders : NavigationRailScreen(
        route = Screen.Orders.route,
        label = R.string.orders_label,
        selectedIcon = R.drawable.ic_orders,
    )

    object Category : NavigationRailScreen(
        route = Screen.Category.route,
        label = R.string.category_label,
        selectedIcon = R.drawable.ic_category,
    )

    object Coupons : NavigationRailScreen(
        route = Screen.Coupons.route,
        label = R.string.coupons_label,
        selectedIcon = R.drawable.ic_coupon,
    )
}