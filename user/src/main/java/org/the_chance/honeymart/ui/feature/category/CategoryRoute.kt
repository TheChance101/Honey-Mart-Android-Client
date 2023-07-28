package org.the_chance.honeymart.ui.feature.category

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.CategoryScreen.route

fun NavController.navigateToCategoryScreen(marketId: Long?) {
    navigate("${ROUTE}/${marketId}")
}

fun NavGraphBuilder.categoryRoute() {
    composable(
        "${ROUTE}/{${MarketArgs.MARKET_ID}}",
        arguments = listOf(
            navArgument(MarketArgs.MARKET_ID) {
                NavType.StringType
            }

        )
    ) {
        CategoryScreen()
    }
}

class MarketArgs(savedStateHandle: SavedStateHandle) {
    val marketId: String = checkNotNull(savedStateHandle[MARKET_ID])

    companion object {
        const val MARKET_ID = "marketId"
    }
}


