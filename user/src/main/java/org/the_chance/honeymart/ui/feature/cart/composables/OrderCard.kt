package org.the_chance.honeymart.ui.feature.cart.composables


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.the_chance.honeymart.ui.feature.wishlist.formatCurrencyWithNearestFraction
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.user.R

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
            .fillMaxWidth()
            .height(MaterialTheme.dimens.card),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary,
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.space16)
        ) {
            val (priceInDollars, orderNowButton, totalPriceText) = createRefs()
            Text(
                text = formatCurrencyWithNearestFraction(totalPrice),
                style = org.the_chance.honymart.ui.theme.Typography.bodyMedium.copy(black60),
                modifier = Modifier.constrainAs(priceInDollars) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end)
                }
            )

            HoneyFilledIconButton(
                label = stringResource(id = R.string.order_now),
                onClick = onClick,
                isEnable = !isLoading,
                iconPainter = painterResource(id = org.the_chance.design_system.R.drawable.icon_cart),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(orderNowButton) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    },
            )
            Text(
                text = stringResource(R.string.total_price),
                style = org.the_chance.honymart.ui.theme.Typography.displaySmall
                    .copy(color = MaterialTheme.colorScheme.onTertiaryContainer),
                modifier = Modifier.constrainAs(totalPriceText) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 8.dp)
                    bottom.linkTo(orderNowButton.top, margin = 8.dp)
                }
            )
        }
    }
}






