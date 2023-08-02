package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.ProductScreen.route

fun NavController.navigateToProductScreen(categoryId: Long ,marketId: Long , position: Int ) {
    navigate("${ROUTE}/${categoryId}/${marketId}/${position}")
}

fun NavGraphBuilder.productRoute() {
    composable(
        route = "${ROUTE}/{${ProductArgs.CATEGORY_ID}}/{${ProductArgs.MARKET_ID}}/{${ProductArgs.POSITION}}",
        arguments = listOf(
            navArgument(name = ProductArgs.CATEGORY_ID) {
                NavType.LongType
            },
            navArgument(name = ProductArgs.MARKET_ID) {
                NavType.LongType
            },
            navArgument(name = ProductArgs.POSITION) {
                NavType.IntType
            }

        )
    ) {
        ProductsScreen()
    }
}


class ProductArgs(savedStateHandle: SavedStateHandle) {
    val categoryId: String = checkNotNull(savedStateHandle[CATEGORY_ID])
    val marketId: String = checkNotNull(savedStateHandle[MARKET_ID])
    val position: String = checkNotNull(savedStateHandle[POSITION])


    companion object {
        const val CATEGORY_ID = "categoryId"
        const val MARKET_ID = "marketId"
        const val POSITION = "position"
    }
}


