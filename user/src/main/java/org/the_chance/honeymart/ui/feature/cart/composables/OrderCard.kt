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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.cart.CartViewModel
import org.the_chance.honeymart.util.formatCurrencyWithNearestFraction
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.user.R

@Composable
fun CartCardView(
    modifier: Modifier = Modifier,
    totalPrice: String = "300,000 $",
    isLoading: Boolean,
    viewModel: CartViewModel = hiltViewModel()
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
            val (priceInDollars, orderNowButton, TotalPrice) = createRefs()
            Text(
                text = formatCurrencyWithNearestFraction(totalPrice.toDouble()),
                color = black60,
                style = org.the_chance.honymart.ui.theme.Typography.bodyMedium,
                modifier = Modifier.constrainAs(priceInDollars) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end)
                }
            )

            CustomButton(
                onClick = { viewModel.onClickOrderNowButton() },
                labelIdStringRes = org.the_chance.design_system.R.string.order_now,
                isEnable = !isLoading,
                idIconDrawableRes = org.the_chance.design_system.R.drawable.icon_cart,
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
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = org.the_chance.honymart.ui.theme.Typography.displaySmall,
                modifier = Modifier.constrainAs(TotalPrice) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 8.dp)
                    bottom.linkTo(orderNowButton.top, margin = 8.dp)
                }
            )
        }
    }
}






