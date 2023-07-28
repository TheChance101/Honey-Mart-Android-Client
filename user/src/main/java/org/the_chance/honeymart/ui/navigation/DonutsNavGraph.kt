package org.the_chance.honeymart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.bottom_navigation.bottomNavGraph
import org.the_chance.honeymart.ui.feature.order_details.orderDetailsRoute

@Composable
fun HoneyMartAppNavGraph() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = Screen.MarketScreen.route
    ) {
        bottomNavGraph()
        orderDetailsRoute()
    }
}

object Graph {
    const val route = "user_graph"
}