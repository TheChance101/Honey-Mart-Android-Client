package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.home.marketRoute
import org.the_chance.honeymart.ui.feature.product_details.productDetailsRoute

fun NavGraphBuilder.HomeNewNavGraph(){
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = Graph.NewHome
    ) {
        marketRoute()
        productDetailsRoute()
    }
}