package org.the_chance.honeymart.ui.feature.cart.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.cart.CartListProductUiState
import org.the_chance.honeymart.ui.feature.cart.formatCurrencyWithNearestFraction
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    product: CartListProductUiState,
    onClickMinus: () -> Unit = {},
    onClickPlus: () -> Unit = {},
    isLoading: Boolean,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = MaterialTheme.dimens.space8),
        colors =
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
        shape = Shapes.medium
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onTertiary)
        ) {
            val (imageOrder, textOrderNumber, textItemPrice, textRow) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = product.productImage[0]),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .width(MaterialTheme.dimens.card)
                    .height(MaterialTheme.dimens.card)
                    .constrainAs(imageOrder) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }, contentScale = ContentScale.Crop
            )

            Text(
                text = product.productName,
                style = org.the_chance.honymart.ui.theme.Typography.displayLarge.copy(
                    MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier.constrainAs(textOrderNumber) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(imageOrder.end, margin = 8.dp)
                }
            )
            Text(
                text = formatCurrencyWithNearestFraction(product.productPrice),
                style = org.the_chance.honymart.ui.theme.Typography.displayLarge.copy(
                    primary100
                ),

                modifier = Modifier
                    .constrainAs(
                        textItemPrice
                    ) {
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        start.linkTo(imageOrder.end, margin = 8.dp)
                        end.linkTo(textRow.start, margin = 8.dp)
                    }
                    .border(1.dp, primary100, CircleShape)
                    .background(Color.Transparent, CircleShape)
                    .padding(
                        horizontal = MaterialTheme.dimens.space8,
                        vertical = MaterialTheme.dimens.space4
                    ),

                )
            Row(
                modifier = Modifier
                    .constrainAs(textRow) {
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        end.linkTo(parent.end, margin = 8.dp)
                    },
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
            ) {

                IconButton(
                    onClick = { onClickMinus() },

                    enabled = !isLoading,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = primary100,
                        disabledContainerColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .border(
                            color = primary100,
                            width = 1.dp,
                            shape = CircleShape
                        )
                        .size(MaterialTheme.dimens.icon24)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus_1),
                        contentDescription = null,
                        tint = primary100,
                        modifier = Modifier.padding(4.dp),
                    )
                }


                Text(
                    text = product.productCount.toString(),
                    style = org.the_chance.honymart.ui.theme.Typography.displayLarge.copy(
                        black60
                    ),
                )


                IconButton(
                    onClick = { onClickPlus() },
                    enabled = !isLoading,
                    modifier = Modifier
                        .background(primary100, CircleShape)
                        .size(MaterialTheme.dimens.icon24)

                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Preview(device = "id:3.2in HVGA slider (ADP1)")
@Composable
private fun CartItemPreview() {
    CartItem(
        product = CartListProductUiState(
            productId = 1,
            productName = "Product Name",
            productPrice = 10000.0,
            productImage = listOf("https://picsum.photos/200/300"),
            productCount = 1
        ),
        onClickMinus = {},
        onClickPlus = {},
        isLoading = false
    )
}