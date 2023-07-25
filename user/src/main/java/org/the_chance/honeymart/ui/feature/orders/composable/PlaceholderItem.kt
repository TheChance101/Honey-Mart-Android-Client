package org.the_chance.honeymart.ui.feature.orders.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun PlaceholderItem(
    modifier: Modifier = Modifier,
    image: Painter,
    title: String,
    subtitle: String,
    onClickDiscoverMarkets: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = image, contentDescription = "PlaceHolder Image")
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = black60
            )
            Text(
                text = subtitle,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = black37
            )
        }
        CustomButton(
            labelIdStringRes = R.string.discover_market_now,
            onClick = onClickDiscoverMarkets,
            idIconDrawableRes = R.drawable.icon_cart,
            background = primary100
        )
    }
}

@Preview
@Composable
fun PreviewPlaceholderItem() {
    PlaceholderItem(
        image = painterResource(id = R.drawable.no_orders_placeholder),
        title = "You Don't have any orders!!",
        subtitle = "It's a catastrophe! Let's fix it by adding items that catch your eye!",
        onClickDiscoverMarkets = { }
    )
}