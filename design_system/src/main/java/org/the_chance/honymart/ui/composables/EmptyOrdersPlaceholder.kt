package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun EmptyOrdersPlaceholder(
    image: Int,
    title: String,
    subtitle: String,
    onClickDiscoverMarkets: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.space16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.space32),
            style = MaterialTheme.typography.bodyMedium,
            color = black60,
            textAlign = TextAlign.Center
        )
        Text(
            text = subtitle,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.space16),
            style = MaterialTheme.typography.displayLarge,
            color = black37,
            textAlign = TextAlign.Center,
        )

        CustomButton(
            labelIdStringRes = R.string.discover_market_now,
            onClick = onClickDiscoverMarkets,
            idIconDrawableRes = R.drawable.icon_cart,
            modifier = Modifier.padding(top = MaterialTheme.dimens.space40),
            background = primary100
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewPlaceholderItem() {
    HoneyMartTheme {
        EmptyOrdersPlaceholder(
            image = R.drawable.placeholder_order,
            title = stringResource(R.string.placeholder_title),
            subtitle = stringResource(R.string.placeholder_subtitle),
            onClickDiscoverMarkets = { }
        )
    }
}