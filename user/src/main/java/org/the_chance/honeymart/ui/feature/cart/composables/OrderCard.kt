package org.the_chance.honeymart.ui.feature.cart.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.wishlist.formatCurrencyWithNearestFraction
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CartCardView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    totalPrice: Double,
    isLoading: Boolean
) {
    Card(
        shape = RoundedCornerShape(
            topEnd = MaterialTheme.dimens.space16,
            topStart = MaterialTheme.dimens.space16,
            bottomEnd = MaterialTheme.dimens.zero,
            bottomStart = MaterialTheme.dimens.zero
        ),
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding( MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.total_price),
                style = org.the_chance.honymart.ui.theme.Typography.displaySmall
                    .copy(color = MaterialTheme.colorScheme.onTertiaryContainer),
                modifier = Modifier
            )
            Text(
                text = formatCurrencyWithNearestFraction(totalPrice),
                style = MaterialTheme.typography.bodyMedium
                    .copy(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
            )
        }

        HoneyFilledIconButton(
            label = stringResource(id = R.string.order_now),
            onClick = onClick,
            isEnable = !isLoading,
            iconPainter = painterResource(id = R.drawable.icon_cart),
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.space16),
        )
    }
}

@Preview
@Composable
fun CartCardViewPreview() {
    HoneyMartTheme {
        CartCardView(
            onClick = {},
            totalPrice = 100.0,
            isLoading = false
        )
    }
}






