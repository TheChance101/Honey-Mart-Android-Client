package org.the_chance.honeymart.ui.feature.category

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.CategoryScreen.route

fun NavController.navigateToCategoryScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.categoryRoute() {
    composable(ROUTE) { CategoryScreen() }
}