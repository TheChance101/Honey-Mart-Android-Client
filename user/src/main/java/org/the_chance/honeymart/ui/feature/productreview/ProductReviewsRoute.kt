package org.the_chance.honeymart.ui.feature.productreview

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.ProductReviewsScreen.route

fun NavController.navigateToProductReviewsScreen(productId: Long) {
    navigate("${ROUTE}/$productId")
}


fun NavGraphBuilder.productReviewsRoute() {
    composable(
        route = "${ROUTE}/{${ProductReviewsArgs.PRODUCT_ID}}",
        arguments = listOf(
            navArgument(name = ProductReviewsArgs.PRODUCT_ID) {
                NavType.LongType
            }
        )
    ) {
        ProductReviewsScreen()
    }
}

class ProductReviewsArgs(savedStateHandle: SavedStateHandle) {
    val productId: String = checkNotNull(savedStateHandle[PRODUCT_ID])

    companion object {
        const val PRODUCT_ID = "productId"
    }
}
