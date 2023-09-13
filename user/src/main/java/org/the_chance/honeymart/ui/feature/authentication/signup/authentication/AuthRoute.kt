package org.the_chance.honeymart.ui.feature.authentication.signup.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.AuthScreen.route

fun NavController.navigateToAuthScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.authRoute() {
    composable(ROUTE) { AuthScreen() }
}