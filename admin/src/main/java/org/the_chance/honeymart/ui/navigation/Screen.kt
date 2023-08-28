package org.the_chance.honeymart.ui.navigation

sealed class Screen(val route: String) {
    object Requests : Screen("Requests")
    object Login : Screen("Login")
}
