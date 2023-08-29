package org.the_chance.honeymart.ui.feature.cart.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.cart.CartListProductUiState
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

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
            .height(MaterialTheme.dimens.space100),
        colors =
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = product.productImageUrl),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .weight(0.5f)
                    .height(MaterialTheme.dimens.card),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(
                        vertical = MaterialTheme.dimens.space16,
                        horizontal = MaterialTheme.dimens.space8
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.productName,
                    style = MaterialTheme.typography.displayLarge.copy(
                        MaterialTheme.colorScheme.onSecondary
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .border(
                                width = MaterialTheme.dimens.space1,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .background(
                                color = Color.Transparent,
                                shape = CircleShape
                            )
                            .padding(
                                horizontal = MaterialTheme.dimens.space8,
                                vertical = MaterialTheme.dimens.space4
                            ),
                        text = product.productPriceFormatted,
                        style = MaterialTheme.typography.displayLarge.copy(
                            MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { onClickMinus() },
                        enabled = !isLoading,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .border(
                                color = MaterialTheme.colorScheme.primary,
                                width = MaterialTheme.dimens.space1,
                                shape = CircleShape
                            )
                            .size(MaterialTheme.dimens.icon24)

                    ) {
                        Icon(
                            modifier = Modifier.padding(MaterialTheme.dimens.space4),
                            painter = painterResource(id = R.drawable.minus_1),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }

                    Text(
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.dimens.space8
                        ),
                        text = product.productCount.toString(),
                        style = MaterialTheme.typography.displayLarge.copy(
                            MaterialTheme.colorScheme.onSecondary
                        )
                    )


                    IconButton(
                        onClick = { onClickPlus() },
                        enabled = !isLoading,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .size(MaterialTheme.dimens.icon24)

                    ) {
                        Icon(
                            modifier = Modifier.padding(MaterialTheme.dimens.space4),
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 320)
@Composable
private fun CartItemPreview() {
    HoneyMartTheme {
        CartItem(
            product = CartListProductUiState(
                productId = 1,
                productName = "Product Name",
                productPrice = 10000.0,
                productImage = listOf("https://picsum.photos/200/300"),
                productCount = 100
            ),
            onClickMinus = {},
            onClickPlus = {},
            isLoading = false
        )
    }
}