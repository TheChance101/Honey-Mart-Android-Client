package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.orders.OrderDetailsProductUiState
import org.the_chance.honeymart.ui.util.toCountFormat
import org.the_chance.honeymart.ui.util.toPriceFormat
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.blackOn60
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderDetailsCard(
    onClick: () -> Unit,
    state: OrderDetailsProductUiState,
    modifier: Modifier = Modifier,
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
            imageUrl = state.productImageUrl
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
                    text = state.name,
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
                        painter = painterResource(id = R.drawable.ic_cart_check),
                        contentDescription = stringResource(id = R.string.icon_cart),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = state.count.toCountFormat(),
                        style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.onSecondaryContainer)
                    )
                }
            }
            Text(
                text = state.price.toPriceFormat(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
