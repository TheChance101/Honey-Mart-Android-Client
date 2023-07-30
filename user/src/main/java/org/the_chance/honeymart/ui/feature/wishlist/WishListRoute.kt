package org.the_chance.honeymart.ui.feature.wishlist



import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.WishListScreen.route

fun NavController.navigateToWishListScreen() {
    navigate(ROUTE)
}

fun NavGraphBuilder.wishListRoute() {
    composable(ROUTE) { WishListScreen() }
}