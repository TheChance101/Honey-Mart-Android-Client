package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.order_details.orderDetailsRoute
import org.the_chance.honeymart.ui.feature.orders.orderRoute
import org.the_chance.honeymart.ui.feature.product_details.productDetailsRoute

fun NavGraphBuilder.orderNavGraph() {
    navigation(
        startDestination = Screen.OrderScreen.route,
        route = Graph.ORDERS
    ) {
        orderRoute()
        orderDetailsRoute()
        productDetailsRoute()
    }
}