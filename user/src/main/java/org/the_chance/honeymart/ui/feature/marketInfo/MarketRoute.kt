package org.the_chance.honeymart.ui.feature.marketInfo

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.MarketInfoScreen.route

fun NavController.navigateToMarketInfoScreen(marketId: Long) {
    navigate("${ROUTE}/${marketId}")
}

fun NavGraphBuilder.marketInfoRoute() {
    composable(
        "${ROUTE}/{${CategoryArgs.MARKET_ID}}",
        arguments = listOf(
            navArgument(CategoryArgs.MARKET_ID) {
                NavType.LongType
            }
        )
    ) {
        MarketInfoScreen()
    }
}

class CategoryArgs(savedStateHandle: SavedStateHandle) {
    val marketId: String = checkNotNull(savedStateHandle[MARKET_ID])

    companion object {
        const val MARKET_ID = "marketId"
    }
}


