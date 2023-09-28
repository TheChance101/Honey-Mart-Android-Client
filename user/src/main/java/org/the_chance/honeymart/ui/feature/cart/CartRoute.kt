package org.the_chance.honeymart.ui.feature.cart

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.CartScreen.route

fun NavController.navigateToCartScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.cartRoute() {
    composable(ROUTE) { CartScreen() }
}