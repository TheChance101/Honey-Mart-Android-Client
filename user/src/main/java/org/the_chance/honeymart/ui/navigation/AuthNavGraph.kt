package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.authentication.authRoute
import org.the_chance.honeymart.ui.feature.login.loginRoute
import org.the_chance.honeymart.ui.feature.signup.signupRoute

fun NavGraphBuilder.authNavGraph() {

    navigation(
        route = Graph.AUTH_GRAPH,
        startDestination = Screen.AuthenticationScreen.route
    ) {
        authRoute()
        loginRoute()
        signupRoute()
    }
}