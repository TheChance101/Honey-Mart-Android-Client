package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.orders.orderRoute
import org.the_chance.honeymart.ui.feature.profile.profileRoute

fun NavGraphBuilder.profileNavGraph() {
    navigation(
        startDestination = Screen.OrderScreen.route,
        route = Graph.PROFILE
    ) {
        profileRoute()
        orderRoute()
    }
}