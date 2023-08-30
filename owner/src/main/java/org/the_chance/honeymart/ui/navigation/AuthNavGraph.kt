package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.features.authentication.login.loginRoute
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.MarketInfoRoute
import org.the_chance.honeymart.ui.features.authentication.signup.signupRoute
import org.the_chance.honeymart.ui.features.authentication.waitingApprove.WaitingApproveRoute

fun NavGraphBuilder.authNavGraph() {
    navigation(
        startDestination = Screen.Login.route,
        route = Graph.AUTH_GRAPH
    ) {
        signupRoute()
        loginRoute()
        MarketInfoRoute()
        WaitingApproveRoute()
    }
}