package org.the_chance.honeymart.ui.navigation

sealed class Screen(val route: String) {
    object AuthenticationScreen : Screen("authenticationScreen")
    object LoginScreen : Screen("loginScreen")
    object SignupScreen : Screen("signupScreen")

    object MarketScreen : Screen("marketScreen")
    object CartScreen : Screen("cartScreen")
    object OrderScreen : Screen("orderScreen")
    object WishListScreen : Screen("wishListScreen")

    object CategoryScreen : Screen("categoryScreen")
    object OrderDetailsScreen : Screen("orderDetailsScreen")
    object ProductScreen : Screen("ProductScreen")
    object ProductDetailsScreen : Screen("productDetailsScreen")



}
