package org.the_chance.honeymart.ui.feature.coupons

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.CouponsScreen.route

fun NavController.navigateToCouponsScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.couponsRoute() {
    composable(route = ROUTE) { CouponsScreen() }
}