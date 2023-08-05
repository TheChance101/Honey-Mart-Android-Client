package org.the_chance.honeymart.ui.products.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.blackOn60
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductCard(
    imageUrl: String,
    productName: String,
    productPrice: String,
    productQuantity: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
    ) {
        ImageNetwork(
            modifier = Modifier
                .size(MaterialTheme.dimens.itemProductImage)
                .clip(CircleShape),
            imageUrl = imageUrl
        )
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.dimens.space12,
                        bottom = MaterialTheme.dimens.space12
                    )
            ) {
                Text(
                    text = productName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = blackOn60
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = productPrice,
                    style = MaterialTheme.typography.bodyMedium,
                    color = blackOn60
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.dimens.icon24),
                        painter = painterResource(id = R.drawable.icon_cart_details),
                        contentDescription = "",
                        tint = black37
                    )
                    Text(
                        text = productQuantity,
                        style = MaterialTheme.typography.displayLarge,
                        color = black60,
                    )
                }
            }
        }
    }
}

@Preview(name = "tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewProductCard() {
    HoneyMartTheme {
        ProductCard(
            imageUrl = "",
            productName = "Fresh fruits",
            productPrice = "30,000$",
            productQuantity = "25 item"
        )
    }
}