package org.the_chance.honeymart.ui.navigation

sealed class Screen(val route: String) {
    object AuthenticationScreen : Screen("authenticationScreen")
    object LoginScreen : Screen("loginScreen")
    object SignupScreen : Screen("signupScreen")

    object HomeScreen : Screen("homeScreen")
    object CartScreen : Screen("cartScreen")
    object OrderScreen : Screen("orderScreen")
    object WishListScreen : Screen("wishListScreen")
    object ProfileScreen : Screen("profileScreen")

    object CategoryScreen : Screen("categoryScreen")
    object CategoryScreenWithArgs : Screen("categoryScreen/{marketId}")
    object OrderDetailsScreen : Screen("orderDetailsScreen")
    object OrderDetailsScreenWithArgs : Screen("orderDetailsScreen/{orderId}")
    object ProductScreen : Screen("ProductScreen")
    object ProductScreenWithArgs : Screen("ProductScreen/{categoryId}/{marketId}/{position}")
    object ProductDetailsScreen : Screen("productDetailsScreen")
    object SearchScreen : Screen("searchScreen")
    object NewProductsScreen : Screen("newProductsScreen")
    object NotificationsScreen : Screen("notificationsScreen")


}
