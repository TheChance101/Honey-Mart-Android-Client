package org.the_chance.honeymart.ui.features.requests

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Graph
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.Requests.route

fun NavController.navigateToRequestsScreen() {
    navigate(ROUTE) {
        popUpTo(route = Graph.AUTH_GRAPH) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.requestsRoute() {
    composable(
        route = ROUTE,
    ) {
        RequestsScreen()
    }
}