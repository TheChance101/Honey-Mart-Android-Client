package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.features.requests.requestsRoute


fun NavGraphBuilder.mainNavGraph() {
    navigation(
        startDestination = Screen.Requests.route,
        route = Graph.MAIN_GRAPH
    ) {
        requestsRoute()
    }
}