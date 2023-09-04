package org.the_chance.honeymart.ui.feature.authentication.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.SignupScreen.route

fun NavController.navigateToSignupScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.signupRoute() {
    composable(ROUTE) { SignupScreen() }
}