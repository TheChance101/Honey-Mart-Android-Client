package org.the_chance.honeymart.ui.features.coupons.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.components.Placeholder
import org.the_chance.honeymart.ui.features.coupons.AddCouponInteractionListener
import org.the_chance.honeymart.ui.features.coupons.AddCouponUiState
import org.the_chance.honeymart.ui.features.coupons.composables.DateField
import org.the_chance.honeymart.ui.features.coupons.composables.coupon.CouponItem
import org.the_chance.honeymart.ui.features.coupons.showButton
import org.the_chance.honeymart.ui.features.coupons.showCoupon
import org.the_chance.honeymart.ui.features.coupons.showEmptyPlaceHolder
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun AddCouponContent(
    state: AddCouponUiState,
    listener: AddCouponInteractionListener,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = MaterialTheme.dimens.space16,
            )
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.onTertiary,
                shape = MaterialTheme.shapes.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FormHeader(
            title = stringResource(R.string.add_new_coupon),
            iconPainter = painterResource(id = R.drawable.icon_add_new_category)
        )

        FormTextField(
            text = state.discountPercentage,
            hint = stringResource(R.string.offer_percentage),
            keyboardType = KeyboardType.Number,
            onValueChange = { listener.onDiscountPercentageChange(it) },
            errorMessage = when (state.discountPercentageState) {
                ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.discount_percentage_can_t_be_blank)
                ValidationState.INVALID_COUPON_DISCOUNT_PERCENTAGE -> stringResource(R.string.invalid_coupon_discount_percentage)
                else -> ""
            }
        )

        FormTextField(
            text = state.couponCount,
            hint = stringResource(R.string.coupon_count),
            keyboardType = KeyboardType.Number,
            onValueChange = { listener.onCouponCountChange(it) },
            errorMessage = when (state.couponCountState) {
                ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.coupon_count_can_t_be_blank)
                ValidationState.INVALID_COUPON_COUNT -> stringResource(R.string.invalid_coupon_count)
                else -> ""
            }
        )

        DateField(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space8
                ),
            text = state.expirationDateFormatted,
            onClick = listener::onClickShowDatePicker
        )

        ContentVisibility(state = state.showEmptyPlaceHolder()) {
            Placeholder(
                painter = painterResource(id = R.drawable.owner_empty_order),
                text = stringResource(R.string.select_product_to_start_adding_your_coupon),
            )
        }
        AnimatedVisibility(
            visible = state.showCoupon(),
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.space16),
            ) {
                CouponItem(
                    modifier = Modifier.align(Alignment.Center),
                    coupon = state.coupon,
                )
            }
        }
        Loading(state = state.isLoading)
        AnimatedVisibility(visible = state.showCoupon()) {
            HoneyFilledIconButton(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space24
                ),
                label = stringResource(R.string.add),
                iconPainter = painterResource(R.drawable.icon_add_to_cart),
                onClick = listener::onAddCouponClick,
                isEnable = state.showButton()
            )
        }
    }
}