package org.the_chance.honeymart.ui.feature.coupons.composables

import org.the_chance.honeymart.ui.composables.coupon.CouponsItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.the_chance.honeymart.ui.composables.coupon.CouponUiState
import org.the_chance.honeymart.ui.feature.coupons.CouponsInteractionListener
import org.the_chance.honeymart.ui.feature.coupons.CouponsUiState
import org.the_chance.honeymart.ui.feature.coupons.all
import org.the_chance.honeymart.ui.feature.coupons.expired
import org.the_chance.honeymart.ui.feature.coupons.valid
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun CouponsContentScreen(
    state: CouponsUiState,
    listener: CouponsInteractionListener
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        contentPadding = PaddingValues(bottom = MaterialTheme.dimens.space16),
        columns = GridCells.Fixed(2)
    ) {

        item(
            span = { GridItemSpan(2) },
        ) {
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
        }


        item(
            span = { GridItemSpan(2) },
        ) {
            Coupons(
                coupons = state.coupons,
                onClickCoupon = listener::onClickGetCoupon
            )
        }

    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Coupons(
    coupons: List<CouponUiState>,
    onClickCoupon: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.dimens.space16,
            vertical = MaterialTheme.dimens.space8
        )
    )
    {
        items(items = coupons, key = { it.couponId }) { coupon ->
            CouponsItem(
                modifier = Modifier.animateItemPlacement(),
                coupon = coupon,
                onClickGetCoupon = { onClickCoupon(coupon.couponId) })
        }
    }
}