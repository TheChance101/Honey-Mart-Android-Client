package org.the_chance.honeymart.ui.feature.coupons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.feature.coupons.composables.CouponsContentScreen
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading


@Composable
fun CouponsScreen(
    viewModel: CouponsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is CouponsUiEffect.NavigateToProductsDetailsScreenEffect -> navController.navigateToProductDetailsScreen(
                    it.productId
                )
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getData()
    }

    CouponsContent(
        state = state,
        listener = viewModel
    )
}


@Composable
fun CouponsContent(
    state: CouponsUiState,
    listener: CouponsInteractionListener,
) {

    AppBarScaffold {
        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = listener::getData
        )
        Loading(state.isLoading && state.coupons.isEmpty())

//        ShowEmptyPlaceholder(
//            state = !state.showCouponsContent(),
//            title = stringResource(R.string.empty_coupons),
//            description = stringResource(R.string.there_is_no_coupons_here),
//        )

        ContentVisibility(
            state = state.showCouponsContent()
        ) {
            CouponsContentScreen(
                state = state,
                listener = listener
            )
            Loading(state = state.isLoading && state.coupons.isNotEmpty())
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    CouponsScreen()
}
