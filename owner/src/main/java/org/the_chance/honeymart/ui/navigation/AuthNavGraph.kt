package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.features.login.loginRoute
import org.the_chance.honeymart.ui.features.signup.market_info.MarketInfoRoute
import org.the_chance.honeymart.ui.features.signup.signupRoute

fun NavGraphBuilder.authNavGraph() {
    navigation(
        startDestination = Screen.Login.route,
        route = Graph.AUTH_GRAPH
    ) {
        signupRoute()
        loginRoute()
        MarketInfoRoute()
    }
}