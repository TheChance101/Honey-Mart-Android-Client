package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.blackOn60
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    productName: String = "",
    productPrice: String = "",
    descrption: String = "",
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.icon_cart),
                            contentDescription = "cart icon",
                            tint = black37
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = descrption,
                            style = MaterialTheme.typography.bodyMedium.copy(color = blackOn60)
                        )
                    }

                }
                Text(
                    text = productPrice,
                    style = MaterialTheme.typography.bodyMedium,
                    color = blackOn60
                )
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

        )
    }
}