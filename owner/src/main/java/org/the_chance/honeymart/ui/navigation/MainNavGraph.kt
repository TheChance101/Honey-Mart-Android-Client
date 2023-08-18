package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.features.category.categoryRoute
import org.the_chance.honeymart.ui.features.orders.ordersRoute


fun NavGraphBuilder.mainNavGraph() {
    navigation(
        startDestination = Screen.Category.route,
        route = Graph.MAIN_GRAPH
    ) {
        ordersRoute()
        categoryRoute()
    }
}