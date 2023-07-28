package org.the_chance.honeymart.ui.feature.bottom_navigation

import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.navigation.Screen

sealed class BottomBarScreen(
    val route: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int
) {
    object Market : BottomBarScreen(
        route =  Screen.MarketScreen.route,
        selectedIcon = R.drawable.icon_market_nav,
        unSelectedIcon = R.drawable.icon_market_nav
    )

    object Cart : BottomBarScreen(
        route = Screen.CartScreen.route,
        selectedIcon = R.drawable.icon_cart_nav,
        unSelectedIcon = R.drawable.icon_cart_nav
    )

    object Order : BottomBarScreen(
        route = Screen.OrderScreen.route,
        selectedIcon = R.drawable.icon_order_nav,
        unSelectedIcon = R.drawable.icon_order_nav
    )

    object WishList : BottomBarScreen(
        route = Screen.WishListScreen.route,
        selectedIcon = R.drawable.icon_favorite_nav,
        unSelectedIcon = R.drawable.icon_favorite_nav
    )
}
