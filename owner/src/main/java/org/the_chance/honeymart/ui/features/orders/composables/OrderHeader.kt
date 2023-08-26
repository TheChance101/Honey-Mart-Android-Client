package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.orders.OrderUiState
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.util.toCountProductFormat
import org.the_chance.honymart.ui.composables.HoneyOutlineText
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun OrderHeader(
    state: OrdersUiState,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val selectedColor by animateColorAsState(
        targetValue = if (isSelected) primary100 else Color.Transparent,
        label = "Selected color"
    )

    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .height(MaterialTheme.dimens.itemOrder),
        colors = CardDefaults.cardColors(containerColor = white)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .background(color = selectedColor)
                    .fillMaxHeight()
                    .width(MaterialTheme.dimens.space8)
            )
            Column(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.dimens.space24,
                    vertical = MaterialTheme.dimens.space8
                ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.order, state.orderId),
                        color = black60,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = state.products.size.toCountProductFormat(),
                        color = black60,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    HoneyOutlineText(text = state.orderDetails.totalPrice)
                }


            }

        }
    }
}
