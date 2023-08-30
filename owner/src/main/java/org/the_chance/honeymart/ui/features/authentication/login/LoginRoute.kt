package org.the_chance.honeymart.ui.features.authentication.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Graph
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.Login.route

fun NavController.navigateToLogin() {
    navigate(ROUTE) {
        popUpTo(route = Graph.MAIN_GRAPH) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.loginRoute() {
    composable(org.the_chance.honeymart.ui.features.authentication.login.ROUTE) { org.the_chance.honeymart.ui.features.authentication.login.LoginScreen() }
}