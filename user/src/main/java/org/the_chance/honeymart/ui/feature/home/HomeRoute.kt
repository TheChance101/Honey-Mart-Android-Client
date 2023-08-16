package org.the_chance.honeymart.ui.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.HomeScreen.route

fun NavController.navigateToHomeScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.marketRoute() {
    composable(ROUTE) { HomeScreen() }
}

