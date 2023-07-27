package org.the_chance.honeymart.ui.feature.orders.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Typography
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
        Image(
            modifier =Modifier.size(160.dp) ,
            painter = image,
            contentDescription = stringResource(org.the_chance.user.R.string.content_desc_of_placeholder_image)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.typography.bodyMedium.color.copy(.6F)
            )
            Text(
                text = subtitle,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.typography.displayLarge.color.copy(.6F)
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
    HoneyMartTheme {
        PlaceholderItem(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
            image = painterResource(id = R.drawable.placeholder_order),
            title = stringResource(R.string.placeholder_title),
            subtitle = stringResource(R.string.placeholder_subtitle),
            onClickDiscoverMarkets = { }
        )
    }
}