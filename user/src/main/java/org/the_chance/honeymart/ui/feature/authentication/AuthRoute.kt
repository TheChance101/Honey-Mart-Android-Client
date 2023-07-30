package org.the_chance.honeymart.ui.feature.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Graph
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.AuthenticationScreen.route

fun NavController.navigateToAuth() {
    navigate(Graph.AUTH_GRAPH)
}

fun NavGraphBuilder.authRoute() {
    composable(ROUTE) { AuthScreen() }
}