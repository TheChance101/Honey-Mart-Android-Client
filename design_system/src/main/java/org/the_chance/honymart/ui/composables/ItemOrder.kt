package org.the_chance.honymart.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemOrder(
    imageUrl: String,
    orderId: Long,
    marketName: String,
    quantity: Int,
    price: Double,
    onClickCard: (orderId: Long) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp)
            .clickable { onClickCard(orderId) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            ImageNetwork(
                imageUrl = imageUrl, modifier = Modifier.width(104.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        horizontal = MaterialTheme.dimens.space8,
                        vertical = MaterialTheme.dimens.space16,
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.order, orderId),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.typography.displayLarge.color.copy(.6F)
                )
                Text(
                    text = marketName,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.typography.displaySmall.color.copy(.6F)
                )
                Row {
                    Image(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.order),
                        contentDescription = marketName,
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.typography.displayLarge.color.copy(
                                .6F
                            )
                        )
                    )
                    SpacerHorizontal4()
                    Text(
                        text = stringResource(id = R.string.items, quantity),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.typography.displayLarge.color.copy(.6F)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            TextPrice(
                price = price.toString(),
                modifier = Modifier.padding(
                    bottom = MaterialTheme.dimens.space16,
                    end = MaterialTheme.dimens.space8,
                )
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ItemOrderPreview() {
    HoneyMartTheme {
        ItemOrder(
            "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg",
            orderId = 4011,
            marketName = "Market name",
            quantity = 2,
            price = 1000.0
        )
    }
}