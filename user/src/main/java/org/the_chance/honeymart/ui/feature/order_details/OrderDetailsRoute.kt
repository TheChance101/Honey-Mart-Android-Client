package org.the_chance.honeymart.ui.feature.order_details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.OrderDetailsScreen.route

fun NavController.navigateToOrderDetailsScreen(orderId:Long) {
    navigate("$ROUTE/$orderId")
}


fun NavGraphBuilder.orderDetailsRoute() {
    composable(
        route = "$ROUTE/{${OrderDetailsArgs.ORDER_ID}}",
        arguments = listOf(
            navArgument(name = OrderDetailsArgs.ORDER_ID) {
                NavType.LongType
            }
        )
    ) {
        OrderDetailsScreen()
    }
}

class OrderDetailsArgs(savedStateHandle: SavedStateHandle) {
    val orderId: String = checkNotNull(savedStateHandle[ORDER_ID])

    companion object {
        const val ORDER_ID = "orderId"
    }
}