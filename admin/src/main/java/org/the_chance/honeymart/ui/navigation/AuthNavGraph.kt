package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.features.login.loginRoute

fun NavGraphBuilder.authNavGraph() {
    navigation(
        startDestination = Screen.Login.route,
        route = Graph.AUTH_GRAPH
    ) {
        loginRoute()
    }
}