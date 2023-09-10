package org.the_chance.honeymart.ui.features.coupons.composables.coupon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.coupons.CouponUiState
import org.the_chance.honeymart.ui.features.coupons.ProductUiState
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CouponItem(
    coupon: CouponUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
    ) {
        CouponDetails(
            modifier = Modifier
                .fillMaxHeight(),
            productName = coupon.product.name,
            expirationDate = coupon.expirationDate,
            count = coupon.count,
            productPrice = coupon.product.productPriceCouponFormat,
            discountPercentage = coupon.offerPrice,
        )
        CouponImage(
            modifier = Modifier
                .fillMaxHeight(),
            productImageUrl = coupon.product.imageUrl,
        )
    }
}

@Composable
fun CouponDetails(
    productName: String,
    expirationDate: String,
    count: String,
    productPrice: String,
    discountPercentage: String,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dimens = MaterialTheme.dimens

    Column(
        modifier = modifier
            .clip(
                CouponDetailsShape(
                    cornerRadius = 16.dp,
                    notchRadius = 16.dp
                )
            )
            .background(colors.background)
            .padding(dimens.space16),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = productName,
            style = typography.displayMedium.copy(color = colors.onSecondary),
        )

        CouponDataRow(
            items = listOf(
                Pair(stringResource(R.string.expiration_date), expirationDate),
            )
        )

        CouponDataRow(
            items = listOf(
                Pair(stringResource(R.string.no_deal), count),
                Pair(stringResource(R.string.price), productPrice),
                Pair(stringResource(R.string.offer_price), discountPercentage)
            )
        )
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
                    style = typography.bodySmall.copy(color = colors.onBackground)
                )

                Text(
                    text = item.second,
                    style = typography.displayLarge.copy(
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
    productImageUrl: String,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val dimens = MaterialTheme.dimens

    Column(
        modifier = modifier
            .clip(
                CouponImageShape(
                    middleNotchRadius = 16.dp,
                    sideNotchRadius = 8.dp,
                    sideNotchGap = 8.dp
                )
            )
            .background(colors.primary)
            .padding(MaterialTheme.dimens.space16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageNetwork(
            modifier = Modifier
                .size(156.dp)
                .clip(RoundedCornerShape(dimens.space24)),
            imageUrl = productImageUrl,
            contentDescription = stringResource(R.string.product_image),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(device = "id:pixel_tablet")
@Composable
fun CouponsItemPreview() {
    HoneyMartTheme {
        CouponItem(
            coupon = CouponUiState(
                count = "12",
                offerPrice = "$500",
                expirationDate = "10.8.2023",
                product = ProductUiState(
                    id = 1L,
                    name = "Product Name",
                    imageUrl = "1",
                    price = 1500.0
                )
            )
        )
    }
}


