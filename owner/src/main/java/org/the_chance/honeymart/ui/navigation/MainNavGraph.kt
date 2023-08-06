package org.the_chance.honeymart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.category.categoryRoute
import org.the_chance.honeymart.ui.orders.ordersRoute

@Composable
fun MainNavGraph() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = Screen.Orders.route
    ) {
        ordersRoute()
        categoryRoute()
    }
}
