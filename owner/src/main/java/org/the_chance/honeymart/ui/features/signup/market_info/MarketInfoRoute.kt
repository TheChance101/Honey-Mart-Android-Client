package org.the_chance.honeymart.ui.features.signup.market_info

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.MarketInfo.route

fun NavController.navigateToMarketInfoScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.MarketInfoRoute() {
    composable(ROUTE) { MarketInfoScreen() }
}