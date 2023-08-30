@file:OptIn(ExperimentalFoundationApi::class)

package org.the_chance.honeymart.ui.feature.coupons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyProductPlaceholder
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.feature.home.composables.coupon.CouponsItem
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.Loading
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

    HoneyAppBarScaffold {
        ConnectionErrorPlaceholder(
            state = state.isError  && !state.showCouponsContent(),
            onClickTryAgain = listener::getData
        )

        EmptyProductPlaceholder(
            state = !state.showCouponsContent() && !state.isLoading,
            title = stringResource(R.string.empty_coupons),
            description = stringResource(R.string.there_is_no_coupons_here),
        )

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.spacedBy(
                    MaterialTheme.dimens.space8,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CustomChip(
                    state = state.all(),
                    text = stringResource(R.string.all),
                    onClick = listener::onClickAllCoupons
                )
                CustomChip(
                    state = state.valid(),
                    text = stringResource(R.string.valid),
                    onClick = listener::onClickValidCoupons
                )
                CustomChip(
                    state = state.expired(),
                    text = stringResource(R.string.expired),
                    onClick = listener::onClickExpiredCoupons
                )
            }

            ContentVisibility(
                state = state.showCouponsContent()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space16),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        items = state.updatedCoupons, key = { it.couponId },
                    ) { coupon ->
                        CouponsItem(
                            modifier = Modifier.animateItemPlacement(),
                            coupon = coupon,
                            isExpired = coupon.isExpired,
                            isClipped = coupon.isClipped,
                        )
                    }
                }
            }
        }

        Loading(state.isLoading)
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    CouponsScreen()
}
