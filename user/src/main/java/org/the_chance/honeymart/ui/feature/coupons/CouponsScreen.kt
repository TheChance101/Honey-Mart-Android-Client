package org.the_chance.honeymart.ui.feature.coupons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.ShowEmptyPlaceholder
import org.the_chance.honeymart.ui.feature.coupons.composables.CouponsContentScreen
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.coupon.CouponsItem
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.theme.dimens


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

        Loading(state.isLoading)

        ConnectionErrorPlaceholder(
            state = state.isError ,
            onClickTryAgain = listener::getData
        )

        Column {
            Row(
                modifier = Modifier.padding(
                    start = MaterialTheme.dimens.space16,
                    bottom = MaterialTheme.dimens.space16
                ),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
            ) {
                CustomChip(
                    state = state.all(),
                    text = "All",
                    onClick = listener::onClickAllCoupons
                )
                CustomChip(
                    state = state.valid(),
                    text = "Valid",
                    onClick = listener::onClickValidCoupons
                )
                CustomChip(
                    state = state.expired(),
                    text = "Expired",
                    onClick = listener::onClickExpiredCoupons
                )
            }

            ShowEmptyPlaceholder(
                state = !state.showCouponsContent() && !state.isLoading,
                title = stringResource(R.string.empty_coupons),
                description = stringResource(R.string.there_is_no_coupons_here),
            )

            ContentVisibility(
                state = state.showCouponsContent()
            ) {
                CouponsContentScreen(
                    state = state,
                    listener = listener
                )
            }
        }

    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    CouponsScreen()
}
