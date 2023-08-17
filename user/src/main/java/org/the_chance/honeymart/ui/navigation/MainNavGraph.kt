package org.the_chance.honeymart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import org.the_chance.honeymart.ui.LocalNavigationProvider

@Composable
fun MainNavGraph() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = Graph.NewHome
    ) {
        authNavGraph()
        homeNavGraph()
        cartNavGraph()
        orderNavGraph()
        wishListNavGraph()
        HomeNewNavGraph()

    }
}

object Graph {
    const val NewHome = "new_home_graph"
    const val AUTH_GRAPH = "auth_graph"
    const val HOME = "home_graph"
    const val CART = "cart_graph"
    const val ORDERS = "orders_graph"
    const val WISH_LIST = "wish_list_graph"
}