package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.the_chance.honeymart.ui.navigation.Screen


private val ROUTE = Screen.ProductScreen.route

fun NavController.navigateToProductScreen(categoryId: Long) {
    navigate("${ROUTE}/${categoryId}")
}

fun NavGraphBuilder.productRoute() {
    composable(
        route = "${ROUTE}/${ProductArgs.CATEGORY_ID}",
        arguments = listOf(
            navArgument(name = ProductArgs.CATEGORY_ID) {
                NavType.LongType
            }
        )
    ) {
        ProductsScreen()
    }
}


class ProductArgs(savedStateHandle: SavedStateHandle) {
    val categoryId: String = checkNotNull(savedStateHandle[CATEGORY_ID])


    companion object {
        const val CATEGORY_ID = "categoryId"
    }
}


