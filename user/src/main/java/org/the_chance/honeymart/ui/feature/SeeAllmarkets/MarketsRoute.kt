package org.the_chance.honeymart.ui.feature.SeeAllmarkets

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.MarketsScreen.route

fun NavController.navigateToMarketsScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.marketsRoute() {
    composable(
        route = ROUTE,
    ) {
        MarketsScreen()
    }
}