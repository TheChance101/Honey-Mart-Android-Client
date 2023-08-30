package org.the_chance.honeymart.ui.features.login.waiting_approved

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.WaitingApprove.route

fun NavController.navigateToWaitingApproveScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.WaitingApproveRoute() {
    composable(ROUTE) { WaitingApproveScreen() }
}