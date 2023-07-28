package org.the_chance.honeymart.ui.feature.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.navigation.Graph
import org.the_chance.honeymart.ui.navigation.Screen

fun NavGraphBuilder.authNavGraph() {

    navigation(
        route = Graph.authRoute,
        startDestination = Screen.AuthenticationScreen.route
    ) {
        composable(route = Screen.AuthenticationScreen.route){
//            AuthScreen()
        }

        composable(route = Screen.LoginScreen.route){
//            LoginScreen()
        }

        composable(route = Screen.SignupScreen.route){
//            SignupScreen()
        }
    }
}