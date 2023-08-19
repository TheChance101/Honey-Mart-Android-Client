package org.the_chance.honeymart.ui.feature.home.composables.coupon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.feature.home.CouponUiState
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CouponsItem(
    coupon: CouponUiState,
    onClickGetCoupon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row {
        CouponDetails(
            productName = coupon.product.productName,
            expirationDate = coupon.expirationDate,
            count = coupon.count,
            productPrice = coupon.product.ProductPrice,
            discountPercentage = coupon.discountPercentage,
            onClick = onClickGetCoupon
        )
        CouponImage(
            couponCode = "445902378",
        )
    }
}

@Composable
fun CouponDetails(
    productName: String,
    expirationDate: String,
    count: Int,
    productPrice: Double,
    discountPercentage: Double,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dimens = MaterialTheme.dimens

    Column(
        modifier = Modifier
            .clip(CouponDetailsShape)
            .background(colors.secondaryContainer)
            .padding(
                horizontal = dimens.space16,
                vertical = dimens.space8
            ),
        verticalArrangement = Arrangement.spacedBy(dimens.space8)
    ) {
        Text(
            text = productName,
            style = MaterialTheme.typography.displaySmall.copy(color = colors.onSecondary),
        )

        CouponDataRow(
            items = listOf(
                Pair("Start Date", "10.08.2023"),
                Pair("End Date", expirationDate),
            )
        )

        CouponDataRow(
            items = listOf(
                Pair("No. Deal", count.toString()),
                Pair("Price", productPrice.toString()),
                Pair("Offer Price", discountPercentage.toString())
            )
        )

        Button(
            onClick = { onClick() },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(21.dp)
                .width(74.dp),
            contentPadding = PaddingValues(
                bottom = 2.dp,
                top = 0.dp,
                end = 0.dp,
                start = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.onPrimary,
                disabledContentColor = colors.onPrimary,
                disabledContainerColor = colors.primary.copy(.5F),
            )
        ) {
            Text(
                text = "Get coupon",
                style = typography.titleMedium,
                color = colors.onPrimary
            )
        }
    }
}

@Composable
fun CouponDataRow(items: List<Pair<String, String>>) {

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dimens = MaterialTheme.dimens

    Row(horizontalArrangement = Arrangement.spacedBy(dimens.space16)) {
        items.forEachIndexed { index, item ->
            Column {
                Text(
                    text = item.first,
                    style = typography.displaySmall.copy(color = colors.onBackground)
                )

                Text(
                    text = item.second,
                    style = typography.displaySmall.copy(
                        color = if (index == 2) colors.primary
                        else colors.onSecondary
                    )
                )
            }
        }
    }
}

@Composable
fun CouponImage(
    couponCode: String,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dimens = MaterialTheme.dimens

    Column(
        modifier = Modifier
            .clip(CouponImageShape)
            .background(colors.primary)
            .padding(
                top = dimens.space16, bottom = dimens.space4,
                end = dimens.space16, start = dimens.space16
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .size(dimens.itemProductImage)
                .clip(RoundedCornerShape(dimens.space12)),
            painter = painterResource(id = org.the_chance.design_system.R.drawable.test),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.padding(top = dimens.space4),
            text = "Coupon Code",
            style = typography.titleMedium.copy(color = colors.onPrimary)
        )
        Text(
            text = couponCode,
            style = typography.titleMedium.copy(color = colors.onPrimary)
        )
    }
}


