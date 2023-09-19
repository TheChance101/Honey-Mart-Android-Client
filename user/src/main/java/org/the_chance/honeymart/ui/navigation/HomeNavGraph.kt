package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.marketInfo.categoryRoute
import org.the_chance.honeymart.ui.feature.home.homeRoute
import org.the_chance.honeymart.ui.feature.see_all_markets.marketsRoute
import org.the_chance.honeymart.ui.feature.new_products.newProductsRoute
import org.the_chance.honeymart.ui.feature.product.productRoute
import org.the_chance.honeymart.ui.feature.product_details.productDetailsRoute
import org.the_chance.honeymart.ui.feature.productreview.productReviewsRoute
import org.the_chance.honeymart.ui.feature.search.searchRoute

fun NavGraphBuilder.homeNavGraph() {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = Graph.HOME
    ) {
        productRoute()
        productReviewsRoute()
        productDetailsRoute()
        categoryRoute()
        homeRoute()
        searchRoute()
        marketsRoute()
        newProductsRoute()
    }
}