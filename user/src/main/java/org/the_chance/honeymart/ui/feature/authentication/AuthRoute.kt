package org.the_chance.honeymart.ui.feature.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Graph

private val ROUTE = Graph.authRoute

fun NavController.navigateToAuth() {
    navigate(ROUTE)
}

fun NavGraphBuilder.authRoute() {
    composable(ROUTE) { AuthScreen() }
}