package org.the_chance.honeymart.ui.features.authentication.signup.marketInfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Graph
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.MarketInfo.route

fun NavController.navigateToMarketInfoScreen() {
    navigate(ROUTE){
        popUpTo(route = Graph.AUTH_GRAPH){
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.marketInfoRoute() {
    composable(ROUTE) { MarketInfoScreen() }
}