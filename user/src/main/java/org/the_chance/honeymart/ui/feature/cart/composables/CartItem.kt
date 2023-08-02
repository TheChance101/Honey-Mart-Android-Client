package org.the_chance.honeymart.ui.feature.cart.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.uistate.CartListProductUiState
import org.the_chance.honeymart.util.formatCurrencyWithNearestFraction
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun CartItem(
    product: CartListProductUiState,
    isLoading: Boolean,
    onClickMinus: () -> Unit = {},
    onClickPlus: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
        shape = RoundedCornerShape(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.onTertiary)

        ) {
            val (imageOrder, textOrderNumber, textItemPrice, imageViewMinusOrder,
                textViewNumberOfItems, imageViewAddItem) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = product.productImage?.get(0)),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .width(100.dp)
                    .height(100.dp)
                    .constrainAs(imageOrder) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                , contentScale = ContentScale.Crop
            )

            Text(
                text = product.productName ?: "",
                style = org.the_chance.honymart.ui.theme.Typography.displayLarge.copy(MaterialTheme.colorScheme.onSecondary),
                modifier = Modifier.constrainAs(textOrderNumber) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(imageOrder.end, margin = 8.dp)
                }
            )

            Text(
                text = formatCurrencyWithNearestFraction(product.productPrice!!),
                style = org.the_chance.honymart.ui.theme.Typography.displayLarge.copy(primary100),

                modifier = Modifier
                    .border(1.dp, primary100, CircleShape)
                    .background(Color.Transparent, CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .constrainAs(textItemPrice) {
                        top.linkTo(textOrderNumber.bottom, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        start.linkTo(imageOrder.end, margin = 8.dp)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    }
            )

            Button(
                onClick = { onClickMinus() },
                shape = CircleShape,
                enabled = !isLoading,
                colors = buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = primary100,
                    disabledContainerColor = Color.Transparent,
                ),
                border = BorderStroke(
                    color = primary100,
                    width = 1.dp
                ),
                modifier = Modifier
                    .paint(painter = painterResource(id = R.drawable.minus_1), contentScale = ContentScale.Inside)
                    .size(24.dp)
                    .constrainAs(imageViewMinusOrder) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        end.linkTo(textViewNumberOfItems.start, margin = 16.dp)
                    },
                ) {}


            Text(
                text = product.productCount.toString(),
                style = org.the_chance.honymart.ui.theme.Typography.displayLarge.copy(
                    black60
                ),
                modifier = Modifier.constrainAs(textViewNumberOfItems) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(imageViewAddItem.start, margin = 16.dp)
                }

            )


            IconButton(
                onClick = { onClickPlus() },
                enabled = !isLoading,
                modifier = Modifier
                    .background(primary100, CircleShape)
                    .size(24.dp)
                    .constrainAs(imageViewAddItem) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

