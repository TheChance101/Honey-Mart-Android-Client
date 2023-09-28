package org.the_chance.honeymart.ui.features.coupons.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductCard(
    onClick: () -> Unit,
    imageUrl: String,
    productName: String,
    productPrice: String,
    description: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val colorAnimation by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary
        else Color.Transparent, label = "Color Animation"
    )

    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .height(116.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {

            Divider(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight(),
                thickness = 100.dp,
                color = colorAnimation
            )

            ImageNetwork(
                modifier = Modifier
                    .size(MaterialTheme.dimens.itemProductImage)
                    .clip(CircleShape),
                imageUrl = imageUrl
            )

            Column(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.dimens.space12,
                        bottom = MaterialTheme.dimens.space12
                    )
            ) {
                Text(
                    text = productName,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                )

                Row(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.space8),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.ic_cart_check),
                        contentDescription = stringResource(id = R.string.icon_cart),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Text(
                        modifier = Modifier.width(150.dp),
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSecondaryContainer),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MaterialTheme.dimens.space16),
                text = productPrice,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            )
        }
    }
}


@Preview
@Composable
fun ProductCardPreview() {
    HoneyMartTheme {
        ProductCard(
            onClick = { },
            imageUrl = "https://i.pinimg.com/originals/0a/0b/9a/0a0b9a4b5b5b3b0b5b5b5b5b5b5b5b5b.jpg",
            productName = "Product Name",
            productPrice = "100.000$",
            description = "Description",
            isSelected = false
        )
    }
}

