package org.the_chance.honeymart.ui.feature.coupons.composables

import org.the_chance.honeymart.ui.composables.coupon.CouponsItem
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ShowEmptyPlaceholder
import org.the_chance.honeymart.ui.feature.coupons.CouponsInteractionListener
import org.the_chance.honeymart.ui.feature.coupons.CouponsUiState
import org.the_chance.honeymart.ui.feature.coupons.all
import org.the_chance.honeymart.ui.feature.coupons.expired
import org.the_chance.honeymart.ui.feature.coupons.showCouponsContent
import org.the_chance.honeymart.ui.feature.coupons.valid
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun CouponsContentScreen(
    state: CouponsUiState,
    listener: CouponsInteractionListener
) {

//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            modifier = Modifier.padding(
//                start = MaterialTheme.dimens.space16,
//                bottom = MaterialTheme.dimens.space16
//            ),
//            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
//        ) {
//            CustomChip(
//                state = state.all(),
//                text = "All",
//                onClick = listener::onClickAllCoupons
//            )
//            CustomChip(
//                state = state.valid(),
//                text = "Valid",
//                onClick = listener::onClickValidCoupons
//            )
//            CustomChip(
//                state = state.expired(),
//                text = "Expired",
//                onClick = listener::onClickExpiredCoupons
//            )
//        }
//
//        ShowEmptyPlaceholder(
//            state = !state.showCouponsContent() && !state.isLoading,
//            title = stringResource(R.string.empty_coupons),
//            description = stringResource(R.string.there_is_no_coupons_here),
//        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            contentPadding = PaddingValues(bottom = MaterialTheme.dimens.space16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = state.coupons, key = { it.couponId }) { coupon ->
                CouponsItem(
                    coupon = coupon,
                    onClickGetCoupon = { listener.onClickGetCoupon(coupon.couponId) })
            }
        }
   // }
}