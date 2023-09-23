package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    productName: String = "",
    productPrice: String = "",
    description: String = "",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space8,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
    ) {
        ImageNetwork(
            modifier = Modifier
                .size(MaterialTheme.dimens.itemProductImage)
                .clip(CircleShape),
            imageUrl = imageUrl
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = MaterialTheme.dimens.space12,
                    bottom = MaterialTheme.dimens.space12
                )
        ) {
            Text(
                text = productName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                modifier = Modifier.padding(top = MaterialTheme.dimens.space8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)

            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimens.icon24),
                    painter = painterResource(id = R.drawable.icon_cart),
                    contentDescription = stringResource(id = R.string.icon_cart),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.onSecondaryContainer),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Text(
            text = productPrice,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    HoneyMartTheme {
        ProductCard(
            onClick = {},
            imageUrl = "https://images.unsplash.com/photo-1629789877829-4b3b8b5b5b9f?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDQwfHh6eWJ4Z0J0Z0lBfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80",
            productName = "Product Name",
            productPrice = "Rp 100.000",
            description = "Description"
        )
    }
}
