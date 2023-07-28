package org.the_chance.honeymart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.authentication.authNavGraph

@Composable
fun UserNavGraph() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = Graph.mainRoute
    ) {
        authNavGraph()
        mainNavGraph()
//        bottomNavGraph()
//        orderDetailsRoute()
//        orderRoute()
//        productRoute()
//        productDetailsRoute()
//        wishListRoute()
//        cartRoute()
//        categoryRoute()
//        marketRoute()
    }
}

object Graph {
    const val bottomRoute = "bottom_navigation_graph"
    const val authRoute = "auth_graph"
    const val mainRoute = "main_graph"
}