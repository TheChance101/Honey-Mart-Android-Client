package org.the_chance.honeymart.ui.feature.authentication

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.AuthenticationScreen.route

fun NavController.navigateToAuth() {
    navigate(ROUTE)
}

fun NavGraphBuilder.authRoute() {
//    composable(ROUTE) { AuthScreen() }
}