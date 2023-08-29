package org.the_chance.honeymart.ui.feature.markets.compoaseable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun MarketItem(
    onClickItem: (Long) -> Unit,
    marketId: Long,
    marketImage: String,
    marketName: String,
    modifier: Modifier = Modifier
){
Card(
    modifier = modifier
        .clip(MaterialTheme.shapes.medium)
        .clickable { onClickItem(marketId) }
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        horizontalAlignment = Alignment.Start
    ) {
        ImageNetwork(
            imageUrl = marketImage,
            contentDescription = stringResource(R.string.market_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                    topStart = MaterialTheme.dimens.space16,
                    topEnd = MaterialTheme.dimens.space16))
                .height(MaterialTheme.dimens.heightItemMarketCard),
        )
        Text(
            text = marketName ,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.dimens.space8,
                    bottom = MaterialTheme.dimens.space12
                )
        )
    }
}
}