package org.the_chance.honeymart.ui.features.notifications

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.Notifications.route
const val OWNER_NOTIFICATION_URI = "https://honeymart.owner.ui.feature.notifications"

fun NavGraphBuilder.notificationsRoute() {
    composable(
        route = ROUTE,
        deepLinks = listOf(navDeepLink { uriPattern = OWNER_NOTIFICATION_URI })
    ) {
        NotificationsScreen()
    }
}