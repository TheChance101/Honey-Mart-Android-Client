package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.authentication.login.loginRoute
import org.the_chance.honeymart.ui.feature.authentication.signup.authentication.authRoute
import org.the_chance.honeymart.ui.feature.authentication.signup.signupRoute

fun NavGraphBuilder.authNavGraph() {

    navigation(
        route = Graph.AUTH_GRAPH,
        startDestination = Screen.SignupScreen.route
    ) {
        authRoute()
        loginRoute()
        signupRoute()
    }
}