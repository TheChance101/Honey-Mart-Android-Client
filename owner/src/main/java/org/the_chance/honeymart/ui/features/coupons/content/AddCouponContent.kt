package org.the_chance.honeymart.ui.features.coupons.content

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
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.features.coupons.AddCouponUiState
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.components.Placeholder
import org.the_chance.honeymart.ui.features.coupons.CouponUiState
import org.the_chance.honeymart.ui.features.coupons.ProductUiState
import org.the_chance.honeymart.ui.features.coupons.composables.DateField
import org.the_chance.honeymart.ui.features.coupons.composables.coupon.CouponItem
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@Composable
fun AddCouponContent(
    modifier: Modifier = Modifier,
    state: AddCouponUiState,
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = MaterialTheme.dimens.space16,
            )
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FormHeader(
            title = "Add New Coupon",
            iconPainter = painterResource(id = R.drawable.icon_add_new_category)
        )

        FormTextField(
            text = state.discountPercentage,
            hint = "Discount Percentage",
            keyboardType = KeyboardType.Text,
            onValueChange = {},
            errorMessage = when (state.discountPercentageState) {
                ValidationState.BLANK_TEXT_FIELD -> "Discount Percentage can't be blank"
                ValidationState.INVALID_COUPON_DISCOUNT_PERCENTAGE -> "Invalid Coupon Discount Percentage"
                else -> ""
            }
        )

        FormTextField(
            text = state.couponCount,
            hint = "Coupon Count",
            keyboardType = KeyboardType.Text,
            onValueChange = {},
            errorMessage = when (state.couponCountState) {
                ValidationState.BLANK_TEXT_FIELD -> "Coupon Count can't be blank"
                ValidationState.INVALID_COUPON_COUNT -> "Invalid Coupon Count"
                else -> ""
            }
        )

        DateField(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space8
                ),
            onClick = { /*TODO*/ })

        Placeholder(
            painter = painterResource(id = R.drawable.owner_empty_order),
            text = "Select Product to start adding your coupon",
            visibilityState = false,
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(MaterialTheme.dimens.space16),
        ) {
            CouponItem(
                modifier = Modifier.align(Alignment.Center),
                coupon = state.coupon,
            )
        }

        HoneyFilledIconButton(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space24
            ),
            label = stringResource(R.string.add),
            iconPainter = painterResource(R.drawable.icon_add_to_cart),
            onClick = {},
            isEnable = true
        )


    }
}

@Preview
@Composable
fun AddCouponContentPreview() {
    HoneyMartTheme {
        AddCouponContent(
            state = AddCouponUiState(
                discountPercentage = "",
                discountPercentageState = ValidationState.VALID_TEXT_FIELD,
                couponCount = "",
                couponCountState = ValidationState.VALID_TEXT_FIELD,
                coupon = CouponUiState(
                    couponId = 528882L,
                    count = "12",
                    discountedPrice = "$500",
                    expirationDate = "10.8.2023",
                    product = ProductUiState(
                        productId = 1L,
                        productName = "Product Name",
                        productImageUrl = "http://192.168.1.3:8080/files/market/1/product0.3437805634524187154409634312406392.png",
                        productPrice = "$1500"
                    )
                )
            )
        )
    }

}
