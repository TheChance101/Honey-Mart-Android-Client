package org.the_chance.honeymart.ui.feature.market.compoaseable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import org.the_chance.honeymart.ui.feature.market.MarketUiState
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.user.R


@Composable
fun MarketItem(
    onClickItem: (Long) -> Unit,
    state: MarketUiState,
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = Shapes.medium)
            .height(MaterialTheme.dimens.heightItemMarketCard)
            .clickable(onClick = {
                onClickItem(state.marketId)
            }),

        ) {

        Image(
            painter = rememberAsyncImagePainter(model = state.marketImage),
            contentDescription = stringResource(R.string.market_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = Shapes.medium)
                .height(MaterialTheme.dimens.heightItemMarketCard),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.heightItemMarketCard)
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            Pair(1f, Color(0x5E121212)),
                            Pair(1f, Color(0x5E121212))
                        )
                    )
                )
        ) {}


            Row(
                Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.heightItemMarketCard),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = state.marketName , color = White, maxLines = 2, fontSize = 20.sp)
            }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMarketItem() {
    //MarketItem()
}