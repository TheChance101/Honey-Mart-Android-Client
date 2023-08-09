package org.the_chance.honeymart.ui.feature.bottom_navigation

import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.navigation.Graph

sealed class BottomBarItems(
    val route: String,
    val label: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
) {
    object Home : BottomBarItems(
        route = Graph.HOME,
        label = "Market",
        selectedIcon = R.drawable.icon_market_nav,
        unSelectedIcon = R.drawable.icon_market_nav,
    )

    object Cart : BottomBarItems(
        route = Graph.CART,
        label = "Cart",
        selectedIcon = R.drawable.icon_cart_nav,
        unSelectedIcon = R.drawable.icon_cart_nav
    )

    object Order : BottomBarItems(
        route = Graph.ORDERS,
        label = "Order",
        selectedIcon = R.drawable.icon_order_nav,
        unSelectedIcon = R.drawable.icon_order_nav
    )

    object WishList : BottomBarItems(
        route = Graph.WISH_LIST,
        label = "Wishlist",
        selectedIcon = R.drawable.icon_favorite_nav,
        unSelectedIcon = R.drawable.icon_favorite_nav
    )
}
