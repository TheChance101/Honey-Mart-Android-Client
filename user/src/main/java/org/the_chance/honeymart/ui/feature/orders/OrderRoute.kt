package org.the_chance.honeymart.ui.feature.orders

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.OrderScreen.route

fun NavController.navigateToOrderScreen(cartId:Long) {
    navigate("$ROUTE/$cartId")
}

fun NavGraphBuilder.orderRoute() {
    composable(
        route = "${ROUTE}/${OrderArgs.CART_ID}",
        arguments = listOf(
            navArgument(name = OrderArgs.CART_ID) {
                NavType.LongType
            }
        )
    ) {
        OrdersScreen()
    }
}

class OrderArgs(savedStateHandle: SavedStateHandle) {
    val cartId: String = checkNotNull(savedStateHandle[CART_ID])

    companion object {
        const val CART_ID = "cartId"
    }
}