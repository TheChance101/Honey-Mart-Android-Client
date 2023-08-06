package org.the_chance.honeymart.ui.navigation

sealed class Screen(val route: String) {
    object Orders : Screen("Orders")
    object Category : Screen("Category")
}
