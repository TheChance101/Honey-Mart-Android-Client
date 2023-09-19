package org.the_chance.honeymart.ui.features.notifications

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.Notifications.route

fun NavGraphBuilder.notificationsRoute() {
    composable(
        route = ROUTE,
    ) {
        NotificationsScreen()
    }
}