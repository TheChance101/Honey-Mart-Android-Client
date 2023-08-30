package org.the_chance.honeymart.ui.features.profile.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.profile.MarketInfoUiState
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.success
import org.the_chance.honymart.ui.theme.error


@Composable
fun MarketInfoContent(
    state: MarketInfoUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.market_info),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = if (state.status) painterResource(id = R.drawable.icon_online_market)
                        else painterResource(id = R.drawable.icon_offline_market),
                        contentDescription = stringResource(R.string.icon_market_status)
                    )
                    Text(
                        text = if (state.status) stringResource(R.string.online) else stringResource(
                            R.string.offline
                        ),
                        style = MaterialTheme.typography.displayLarge,
                        color = if (state.status) success else error
                    )
                }
            }
        }
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
//            painter = rememberAsyncImagePainter(state.image),
                painter = painterResource(id = R.drawable.market),
                contentDescription = stringResource(R.string.markets),
                modifier = Modifier.padding(top = 72.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds
            )
        }

        Text(
//            text = state.name,
            text = "Rehab Market",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 72.dp)
        )

        Text(
//            text = state.address,
            text = "blablablablablabla",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
//            text = state.description,
            text = "blablablablablabla",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
private fun PreviewMarketInfoContent() {
    HoneyMartTheme {
        MarketInfoContent(state = MarketInfoUiState())
    }
}