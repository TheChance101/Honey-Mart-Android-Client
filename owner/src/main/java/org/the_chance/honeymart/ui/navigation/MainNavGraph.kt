package org.the_chance.honeymart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.features.category.categoryRoute
import org.the_chance.honeymart.ui.features.login.loginRoute
import org.the_chance.honeymart.ui.features.orders.ordersRoute
import org.the_chance.honeymart.ui.features.signup.signupRoute

@Composable
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
}
