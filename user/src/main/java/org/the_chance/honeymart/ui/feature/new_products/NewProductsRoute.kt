package org.the_chance.honeymart.ui.feature.new_products

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.NewProductsScreen.route

fun NavController.navigateToNewProductsScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.newProductsRoute() {
    composable(ROUTE) { NewProductsScreen() }
}