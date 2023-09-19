package org.the_chance.honeymart.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.composables.EventHandler
import org.the_chance.honeymart.ui.feature.see_all_markets.navigateToMarketsScreen
import org.the_chance.honeymart.ui.feature.authentication.signup.authentication.navigateToAuthScreen
import org.the_chance.honeymart.ui.feature.marketInfo.navigateToCategoryScreen
import org.the_chance.honeymart.ui.feature.home.composables.HomeContentSuccessScreen
import org.the_chance.honeymart.ui.feature.new_products.navigateToNewProductsScreen
import org.the_chance.honeymart.ui.feature.order_details.navigateToOrderDetailsScreen
import org.the_chance.honeymart.ui.feature.orders.navigateToOrderScreen
import org.the_chance.honeymart.ui.feature.product.navigateToProductScreen
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honeymart.ui.feature.search.navigateToSearchScreen
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.Loading

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(initialPage = 1)

    EventHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                HomeUiEffect.UnAuthorizedUserEffect -> navController.navigateToAuthScreen()
                HomeUiEffect.NavigateToSearchScreenEffect -> navController.navigateToSearchScreen()
                HomeUiEffect.NavigateToNewProductsScreenEffect -> navController.navigateToNewProductsScreen()
                is HomeUiEffect.NavigateToMarketScreenEffect -> navController.navigateToCategoryScreen(
                    effect.marketId
                )

                is HomeUiEffect.NavigateToProductsDetailsScreenEffect -> navController.navigateToProductDetailsScreen(
                    effect.productId
                )

                is HomeUiEffect.NavigateToProductScreenEffect -> navController.navigateToProductScreen(
                    effect.categoryId,
                    effect.marketId,
                    effect.position
                )

                HomeUiEffect.NavigateToSeeAllMarketEffect -> navController.navigateToMarketsScreen()
                is HomeUiEffect.NavigateToOrderDetailsScreenEffect -> navController.navigateToOrderDetailsScreen(
                    effect.productId
                )

                HomeUiEffect.NavigateToOrderScreenEffect -> navController.navigateToOrderScreen()
            }
        })

    HomeContent(
        state = state,
        pagerState = pagerState,
        listener = viewModel
    )
    LaunchedEffect(key1 = state.markets.isNotEmpty()) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % 3)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    pagerState: PagerState,
    listener: HomeInteractionListener,
) {

    HoneyAppBarScaffold {
        ConnectionErrorPlaceholder(
            state = state.isConnectionError,
            onClickTryAgain = listener::getData
        )
        Loading(state.isLoading && state.markets.isEmpty())
        ContentVisibility(
            state = state.showHome()
        ) {
            HomeContentSuccessScreen(
                state = state,
                pagerState = pagerState,
                listener = listener
            )
            Loading(state = state.isLoading && state.markets.isNotEmpty())
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}