package org.the_chance.honeymart.ui.feature.product

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.ProductScreen.route

fun NavController.navigateToProductScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.productRoute() {
    composable(ROUTE) { ProductsScreen() }
}