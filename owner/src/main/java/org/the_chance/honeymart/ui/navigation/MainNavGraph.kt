package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.features.category.categoryRoute
import org.the_chance.honeymart.ui.features.orders.ordersRoute

/*@Composable
fun MainNavGraph() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        ordersRoute()
        categoryRoute()
        signupRoute()
        loginRoute()
    }
}*/
fun NavGraphBuilder.mainNavGraph() {
    navigation(
        startDestination = Screen.Category.route,
        route = Graph.HOME
    ) {
        ordersRoute()
        categoryRoute()
    }
}