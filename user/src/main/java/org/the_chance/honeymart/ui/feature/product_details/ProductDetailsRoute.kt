package org.the_chance.honeymart.ui.feature.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.ProductDetailsScreen.route

fun NavController.navigateToProductDetailsScreen(productId: Long) {
    navigate("$ROUTE/$productId")
}


fun NavGraphBuilder.productDetailsRoute() {
    composable(
        route = "$ROUTE/{${ProductDetailsArgs.PRODUCT_ID}}",
        arguments = listOf(
            navArgument(name = ProductDetailsArgs.PRODUCT_ID) {
                NavType.LongType
            }
        )
    ) {
        ProductDetailsFragment()
    }
}

class ProductDetailsArgs(savedStateHandle: SavedStateHandle) {
    val productId: String = checkNotNull(savedStateHandle[PRODUCT_ID])

    companion object {
        const val PRODUCT_ID = "productId"
    }
}