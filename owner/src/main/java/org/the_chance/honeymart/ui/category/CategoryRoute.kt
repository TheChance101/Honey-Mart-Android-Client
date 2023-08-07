package org.the_chance.honeymart.ui.category

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.Category.route

fun NavController.navigateToOrderScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.categoryRoute() {
    composable(
        route = ROUTE,
    ) {
        CategoryScreen()
    }
}