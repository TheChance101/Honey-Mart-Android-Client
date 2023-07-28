package org.the_chance.honeymart.ui.feature.cart.Composeables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import org.the_chance.honeymart.ui.feature.uistate.CartListProductUiState
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.black87
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun CartItem(
    product: CartListProductUiState,
    onClickMinus: () -> Unit = {},
    onClickPlus: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (imageOrder, textOrderNumber, textItemPrice, imageViewMinusOrder,
                textViewNumberOfItems, imageViewAddItem) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = product.productImage!![0]),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentHeight()
                    .width(100.dp)
                    .constrainAs(imageOrder) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                contentScale = ContentScale.Crop
            )

            Text(
                text = product.productName ?: "",
                style = org.the_chance.honymart.ui.theme.Typography.displayLarge.copy(black87),
                modifier = Modifier.constrainAs(textOrderNumber) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                    start.linkTo(imageOrder.end, margin = 8.dp)
                }
            )

            Text(
                text = formatCurrencyWithNearestFraction(product.productPrice!!.toDouble()),
                style = org.the_chance.honymart.ui.theme.Typography.displayLarge.copy(primary100),

                modifier = Modifier
                    .border(1.dp, primary100, CircleShape)
                    .background(Color.Transparent, CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .constrainAs(textItemPrice) {
                        top.linkTo(textOrderNumber.bottom)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        start.linkTo(imageOrder.end, margin = 8.dp)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    }
            )

            Card(
                shape = CircleShape,
                modifier = Modifier
                    .clickable { }
                    .size(32.dp)
                    .constrainAs(imageViewMinusOrder) {
                        top.linkTo(parent.top, margin = 32.dp)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(textViewNumberOfItems.start, margin = 16.dp)
                    }.clickable { onClickMinus() },
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Image(

                    painter = painterResource(id = org.the_chance.design_system.R.drawable.ic_minus),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = product.productCount.toString(),
                style = org.the_chance.honymart.ui.theme.Typography.displayMedium.copy(black60),
                modifier = Modifier.constrainAs(textViewNumberOfItems) {
                    top.linkTo(parent.top, margin = 32.dp)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(imageViewAddItem.start, margin = 16.dp)
                }
            )

            // Plus Button
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .clickable { }
                    .size(32.dp)
                    .constrainAs(imageViewAddItem) {
                        top.linkTo(parent.top, margin = 32.dp)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, margin = 16.dp)
                    }.clickable { onClickPlus() },
                colors = CardDefaults.cardColors(containerColor = primary100)
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }

}

