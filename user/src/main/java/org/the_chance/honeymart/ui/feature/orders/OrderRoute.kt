package org.the_chance.honeymart.ui.feature.orders

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.OrderScreen.route

fun NavController.navigateToOrderScreen() {
    navigate("$ROUTE")
}

fun NavGraphBuilder.orderRoute() {
    composable(
        route = "$ROUTE",
    ) {
        OrdersScreen()
    }
}