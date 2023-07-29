package org.the_chance.honeymart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.authentication.authNavGraph
import org.the_chance.honeymart.ui.feature.bottom_navigation.bottomNavGraph
import org.the_chance.honeymart.ui.feature.cart.cartRoute
import org.the_chance.honeymart.ui.feature.category.categoryRoute
import org.the_chance.honeymart.ui.feature.market.marketRoute
import org.the_chance.honeymart.ui.feature.order_details.orderDetailsRoute
import org.the_chance.honeymart.ui.feature.orders.orderRoute
import org.the_chance.honeymart.ui.feature.product.productRoute
import org.the_chance.honeymart.ui.feature.product_details.productDetailsRoute
import org.the_chance.honeymart.ui.feature.wishlist.wishListRoute

@Composable
fun UserNavGraph() {
    val navController = LocalNavigationProvider.current

    NavHost(
        navController = navController,
        startDestination = Screen.MarketScreen.route
    ) {
        authNavGraph()
        bottomNavGraph()
        orderDetailsRoute()
        orderRoute()
        productRoute()
        productDetailsRoute()
        wishListRoute()
        cartRoute()
        categoryRoute()
        marketRoute()
//        signupRoute()
//        authRoute()
//        loginRoute()
    }
}

object Graph {
    const val bottomRoute = "bottom_navigation_graph"
    const val authRoute = "auth_graph"
    const val mainRoute = "main_graph"
}