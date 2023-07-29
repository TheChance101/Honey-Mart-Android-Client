package org.the_chance.honeymart.ui.feature.market

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import org.the_chance.honeymart.ui.feature.uistate.MarketUiState


@Composable
fun MarketItem(
    state: MarketUiState,
    onClickItem: (Long) -> Unit
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .height(164.dp)
            .clickable(onClick = { onClickItem(state.marketId!!) }),

        ) {

        Image(
            painter = rememberAsyncImagePainter(model = state.marketImage),
            contentDescription = "Market Name",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .height(164.dp),
            )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(164.dp)
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
                    .height(164.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment =  Alignment.CenterVertically
            ) {
                Text(text = state.marketName!! , color = White, maxLines = 2, fontSize = 20.sp)
            }



    }

    
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMarketItem() {
    //MarketItem()
}