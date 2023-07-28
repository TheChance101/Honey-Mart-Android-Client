package org.the_chance.honeymart.ui.feature.bottom_navigation
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.cart.CartScreen
import org.the_chance.honeymart.ui.feature.market.MarketsFragment
import org.the_chance.honeymart.ui.feature.orders.OrdersScreen
import org.the_chance.honeymart.ui.feature.wishlist.WishListScreen
import org.the_chance.honeymart.ui.navigation.Graph


fun NavGraphBuilder.bottomNavGraph() {

    navigation(
        route = Graph.route,
        startDestination = BottomBarScreen.Market.route
    ) {
        composable(route = BottomBarScreen.Market.route){
            MarketsFragment()
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