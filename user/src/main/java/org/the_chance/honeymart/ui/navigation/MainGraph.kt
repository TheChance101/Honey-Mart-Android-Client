package org.the_chance.honeymart.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.the_chance.honeymart.ui.feature.cart.CartScreen
import org.the_chance.honeymart.ui.feature.category.CategoriesScreen
import org.the_chance.honeymart.ui.feature.market.MarketScreen
import org.the_chance.honeymart.ui.feature.order_details.OrderDetailsScreen
import org.the_chance.honeymart.ui.feature.orders.OrdersScreen
import org.the_chance.honeymart.ui.feature.product.ProductsScreen
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsScreen
import org.the_chance.honeymart.ui.feature.wishlist.WishListScreen

fun NavGraphBuilder.mainNavGraph() {

    navigation(
        route = Graph.mainRoute,
        startDestination = Screen.MarketScreen.route
    ) {
        composable(route = Screen.MarketScreen.route){
            MarketScreen()
        }

        composable(route = Screen.CartScreen.route){
            CartScreen()
        }
        composable(route = Screen.OrderScreen.route){
            OrdersScreen()
        }
        composable(route = Screen.WishListScreen.route){
            WishListScreen()
        }
        composable(route = Screen.CartScreen.route){
            CartScreen()
        }
        composable(route = Screen.CategoryScreen.route){
            CategoriesScreen()
        }
        composable(route = Screen.OrderDetailsScreen.route){
            OrderDetailsScreen()
        }
        composable(route = Screen.ProductScreen.route){
            ProductsScreen()
        }
        composable(route = Screen.ProductDetailsScreen.route){
            ProductDetailsScreen()
        }

    }
}