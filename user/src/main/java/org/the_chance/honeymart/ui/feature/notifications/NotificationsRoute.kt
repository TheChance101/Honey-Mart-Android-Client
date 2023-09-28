package org.the_chance.honeymart.ui.feature.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.NotificationsScreen.route
const val USER_NOTIFICATION_URI = "https://honeymart.user.ui.feature.notifications"

fun NavController.navigateToNotificationsScreen() {
    navigate(route = ROUTE)
}

fun NavGraphBuilder.notificationsRoute() {
    composable(
        route = ROUTE,
    deepLinks = listOf(navDeepLink { uriPattern = USER_NOTIFICATION_URI })
    )
    {NotificationsScreen()}
}