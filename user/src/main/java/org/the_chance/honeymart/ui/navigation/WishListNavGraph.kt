package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.product_details.productDetailsRoute
import org.the_chance.honeymart.ui.feature.wishlist.wishListRoute

fun NavGraphBuilder.wishListNavGraph() {
    navigation(
        startDestination = Screen.WishListScreen.route,
        route = Graph.WISH_LIST
    ) {
        wishListRoute()
        productDetailsRoute()
    }
}