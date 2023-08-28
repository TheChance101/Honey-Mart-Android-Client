package org.the_chance.honeymart.ui.feature.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.NotificationsScreen.route

fun NavController.navigateToNotificationsScreen() {
    navigate(route = ROUTE)
}

fun NavGraphBuilder.notificationsRoute() {
    composable(route = ROUTE)
    {NotificationsScreen()}
}