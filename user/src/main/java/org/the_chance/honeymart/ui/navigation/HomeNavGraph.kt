package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.category.categoryRoute
import org.the_chance.honeymart.ui.feature.market.homeRoute
import org.the_chance.honeymart.ui.feature.notifications.notificationsRoute
import org.the_chance.honeymart.ui.feature.product.productRoute
import org.the_chance.honeymart.ui.feature.product_details.productDetailsRoute
import org.the_chance.honeymart.ui.feature.search.searchRoute

fun NavGraphBuilder.homeNavGraph() {
    navigation(
        startDestination = Screen.NotificationsScreen.route,
        route = Graph.HOME
    ) {
        notificationsRoute()
        productRoute()
        productDetailsRoute()
        categoryRoute()
        homeRoute()
        searchRoute()
    }
}