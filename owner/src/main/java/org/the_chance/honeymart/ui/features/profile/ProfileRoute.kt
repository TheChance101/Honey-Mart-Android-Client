package org.the_chance.honeymart.ui.features.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import okhttp3.Route
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.Profile.route

fun NavController.navigateToProfileScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.profileRoute() {
    composable(
        route = ROUTE,
    ) {
        ProfileScreen()
    }
}