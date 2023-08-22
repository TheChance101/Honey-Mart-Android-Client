package org.the_chance.honeymart.ui.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.ProfileScreen.route

fun NavController.navigateToProfileScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.profileRoute() {
    composable(ROUTE) { ProfileScreen() }
}