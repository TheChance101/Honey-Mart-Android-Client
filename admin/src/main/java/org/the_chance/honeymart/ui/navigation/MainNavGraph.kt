package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.features.markets.marketsRoute

fun NavGraphBuilder.mainNavGraph() {
    navigation(
        startDestination = Screen.MARKETS.route,
        route = Graph.MAIN_GRAPH
    ) {
        marketsRoute()
    }
}