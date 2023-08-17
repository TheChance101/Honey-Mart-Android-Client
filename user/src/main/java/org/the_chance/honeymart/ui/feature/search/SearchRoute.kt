package org.the_chance.honeymart.ui.feature.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.SearchScreen.route

fun NavController.navigateToSearchScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.searchRoute() {
    composable(
        route = ROUTE,
    ) {
        SearchScreen()
    }
}