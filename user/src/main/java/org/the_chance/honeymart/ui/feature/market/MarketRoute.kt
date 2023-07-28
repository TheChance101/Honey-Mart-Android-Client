package org.the_chance.honeymart.ui.feature.market

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.MarketScreen.route

fun NavController.navigateToMarketScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.marketRoute() {
    composable(ROUTE) { MarketScreen() }
}