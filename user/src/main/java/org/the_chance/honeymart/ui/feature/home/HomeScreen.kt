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
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.feature.authentication.navigateToAuth
import org.the_chance.honeymart.ui.feature.category.navigateToCategoryScreen
import org.the_chance.honeymart.ui.feature.home.composables.HomeContentSuccessScreen
import org.the_chance.honeymart.ui.feature.product.navigateToProductScreen
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honeymart.ui.feature.search.navigateToSearchScreen
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(initialPage = 1)
    val navController = LocalNavigationProvider.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                HomeUiEffect.UnAuthorizedUserEffect -> navController.navigateToAuth()
                HomeUiEffect.NavigateToSearchScreenEffect -> navController.navigateToSearchScreen()
                is HomeUiEffect.NavigateToMarketScreenEffect -> navController.navigateToCategoryScreen(
                    it.marketId
                )

                is HomeUiEffect.NavigateToProductsDetailsScreenEffect -> navController.navigateToProductDetailsScreen(
                    it.productId
                )

                is HomeUiEffect.NavigateToProductScreenEffect -> navController.navigateToProductScreen(
                    it.categoryId,
                    it.marketId,
                    it.position
                )
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getData()
    }
    HomeContent(
        state = state,
        pagerState = pagerState,
        listener = viewModel
    )


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    pagerState: PagerState,
    listener: HomeInteractionListener,
) {

    AppBarScaffold {
        ConnectionErrorPlaceholder(
            state = state.isConnectionError,
            onClickTryAgain = listener::getData
        )
        Loading(state.isLoading)
        ContentVisibility(
            state = state.showHome()
        ) {
            HomeContentSuccessScreen(
                state = state,
                pagerState = pagerState,
                listener = listener
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
