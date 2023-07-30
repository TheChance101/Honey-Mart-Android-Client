package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.cart.cartRoute
import org.the_chance.honeymart.ui.feature.product_details.productDetailsRoute

fun NavGraphBuilder.cartNavGraph() {
    navigation(
        startDestination = Screen.CartScreen.route,
        route = Graph.CART
    ) {
        cartRoute()
        productDetailsRoute()
    }
}