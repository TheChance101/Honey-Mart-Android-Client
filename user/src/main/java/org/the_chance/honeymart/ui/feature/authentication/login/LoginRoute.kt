package org.the_chance.honeymart.ui.feature.authentication.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.LoginScreen.route

fun NavController.navigateToLogin() {
    navigate(ROUTE)
}

fun NavGraphBuilder.loginRoute() {
    composable(ROUTE) { LoginScreen() }
}