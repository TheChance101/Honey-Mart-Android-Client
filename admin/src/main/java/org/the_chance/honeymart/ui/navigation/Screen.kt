package org.the_chance.honeymart.ui.navigation

sealed class Screen(val route: String) {
    object MARKETS : Screen("Markets")
    object Login : Screen("Login")
}
