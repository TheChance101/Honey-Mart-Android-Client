package org.the_chance.honeymart.ui.features.orders

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.Orders.route

fun NavController.navigateToOrdersScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.ordersRoute() {
    composable(
        route = ROUTE,
    ) {
        OrdersScreen()
    }
}