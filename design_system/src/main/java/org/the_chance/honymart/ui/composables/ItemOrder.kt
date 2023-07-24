package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun ItemOrder(
    imageUrl: String,
    orderId: String,
    nameMarket: String,
    quantity: String,
    price: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )

    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            ImageNetwork(
                imageUrl, modifier = Modifier.width(104.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        horizontal = MaterialTheme.dimens.space8,
                        vertical = MaterialTheme.dimens.space16,
                    )

            ) {
                Text(
                    text = stringResource(id = R.string.order, orderId),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.typography.displayLarge.color.copy(.6F)

                )
                SpacerVertical8()
                Text(
                    text = nameMarket,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.typography.displayLarge.color.copy(.6F)
                )
                SpacerVertical8()
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.order),
                        contentDescription = nameMarket,
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
                price = price,
                modifier = Modifier.padding(
                    bottom = MaterialTheme.dimens.space16,
                    end = MaterialTheme.dimens.space8,
                )
            )
        }

    }
}

@Preview
@Composable
private fun ItemOrderPreview() {
    HoneyMartTheme {
        ItemOrder(
            "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg",
            orderId = "4011",
            nameMarket = "Market name",
            quantity = "2",
            price = "10,000,000"
        )
    }
}