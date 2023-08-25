package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.new_products.newProductsRoute
import org.the_chance.honeymart.ui.feature.orders.orderRoute
import org.the_chance.honeymart.ui.feature.profile.profileRoute

fun NavGraphBuilder.profileNavGraph() {
    navigation(
        startDestination = Screen.NewProductsScreen.route,
        route = Graph.PROFILE
    ) {
        newProductsRoute()
        profileRoute()
        orderRoute()
    }
}