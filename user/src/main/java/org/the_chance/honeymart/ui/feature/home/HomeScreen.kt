package org.the_chance.honeymart.ui.feature.home

import androidx.compose.animation.AnimatedVisibility
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
import org.the_chance.honeymart.ui.feature.category.navigateToCategoryScreen
import org.the_chance.honeymart.ui.feature.home.composables.HomeContentSuccessScreen
import org.the_chance.honeymart.ui.feature.login.navigateToLogin
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
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

    HomeContent(
        state = state,
        pagerState = pagerState,
        listener = viewModel
    )
//    LaunchedEffect(Unit) {
//        while (true) {
//            delay(3000)
//            pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % 3)
//        }
//    }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is HomeUiEffect.NavigateToMarketScreen -> navController.navigateToCategoryScreen(it.marketId)
                is HomeUiEffect.NavigateToProductScreen -> navController.navigateToProductDetailsScreen(it.productId)
                HomeUiEffect.UnAuthorizedUserEffect -> navController.navigateToLogin()
            }
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

    AppBarScaffold {
        ConnectionErrorPlaceholder(state = state.isError, onClickTryAgain = listener::getData)
        Loading(state.isLoading)
        AnimatedVisibility(visible = state.showHome()) {
            HomeContentSuccessScreen(state, pagerState, listener)
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
