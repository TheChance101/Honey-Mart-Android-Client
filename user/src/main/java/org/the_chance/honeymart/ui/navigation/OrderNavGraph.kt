package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.order_details.orderDetailsRoute
import org.the_chance.honeymart.ui.feature.orders.orderRoute
import org.the_chance.honeymart.ui.feature.product_details.productDetailsRoute
import org.the_chance.honeymart.ui.feature.search.searchRoute

fun NavGraphBuilder.orderNavGraph() {
    navigation(
        startDestination = Screen.SearchScreen.route,
        route = Graph.ORDERS
    ) {
        searchRoute()
        orderRoute()
        orderDetailsRoute()
        productDetailsRoute()
    }
}