package org.the_chance.honeymart.ui.feature.bottom_navigation
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.cart.CartScreen
import org.the_chance.honeymart.ui.feature.market.MarketScreen
import org.the_chance.honeymart.ui.feature.orders.OrdersScreen
import org.the_chance.honeymart.ui.feature.wishlist.WishListScreen
import org.the_chance.honeymart.ui.navigation.Graph


fun NavGraphBuilder.bottomNavGraph() {

    navigation(
        route = Graph.bottomRoute,
        startDestination = BottomBarScreen.Market.route
    ) {
        composable(route = BottomBarScreen.Market.route){
            MarketScreen()
        }

        composable(route = BottomBarScreen.Cart.route){
           CartScreen()
        }
        composable(route = BottomBarScreen.Order.route){
            OrdersScreen()
        }
        composable(route = BottomBarScreen.WishList.route){
            WishListScreen()
        }
    }
}