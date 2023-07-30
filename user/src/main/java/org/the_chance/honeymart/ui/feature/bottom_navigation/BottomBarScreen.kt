package org.the_chance.honeymart.ui.feature.bottom_navigation

import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.navigation.Graph

sealed class BottomBarScreen(
    val route: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
) {
    object Home : BottomBarScreen(
        route = Graph.HOME,
        selectedIcon = R.drawable.icon_market_nav,
        unSelectedIcon = R.drawable.icon_market_nav
    )

    object Cart : BottomBarScreen(
        route = Graph.CART,
        selectedIcon = R.drawable.icon_cart_nav,
        unSelectedIcon = R.drawable.icon_cart_nav
    )

    object Order : BottomBarScreen(
        route = Graph.ORDERS,
        selectedIcon = R.drawable.icon_order_nav,
        unSelectedIcon = R.drawable.icon_order_nav
    )

    object WishList : BottomBarScreen(
        route = Graph.WISH_LIST,
        selectedIcon = R.drawable.icon_favorite_nav,
        unSelectedIcon = R.drawable.icon_favorite_nav
    )
}
