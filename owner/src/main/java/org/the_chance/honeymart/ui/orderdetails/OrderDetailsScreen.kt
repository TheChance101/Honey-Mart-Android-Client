package org.the_chance.honeymart.ui.orderdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.features.category.composable.ProductCard
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.blackOn60
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    OrderDetailsContent(state)
}


@Composable
fun OrderDetailsContent(state: OrderDetailsUiState) {

    Loading(state = state.isLoading)
    ContentVisibility(state = state.contentScreen()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = MaterialTheme.dimens.space24,
                    start = MaterialTheme.dimens.space16,
                    end = MaterialTheme.dimens.space16,
                    bottom = MaterialTheme.dimens.space24,
                )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = state.formatOrder(state.orderDetails.orderId),
                        style = MaterialTheme.typography.headlineLarge.copy(color = blackOn60),
                    )


                    Row(
                        modifier = Modifier.padding(top = MaterialTheme.dimens.space16),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(MaterialTheme.dimens.space20),
                            painter = painterResource(id = R.drawable.icon_clock),
                            contentDescription = "clock icon",
                            tint = black37
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.space8))
                        Text(
                            text = state.orderDetails.date,
                            style = MaterialTheme.typography.bodyMedium.copy(color = black37),

                            )
                    }
                }

                Text(
                    text = state.formatPrice(state.orderDetails.totalPrice),
                    style = MaterialTheme.typography.bodyMedium.copy(color = blackOn60),

                    )

            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space24)
            ) {
                items(state.products.size) { index ->
                    val product = state.products[index]
//                    ProductCard(
//                        imageUrl = product.images[0],
//                        productName = product.name,
//                        productPrice = product.price.toString(),
//                    )
                }
            }

            HoneyFilledIconButton(
                label = "Done",
                onClick = {},
                iconPainter = painterResource(id = R.drawable.icon_cart)
            )

        }
    }
}


@Preview(name = "tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewProducts() {
    HoneyMartTheme {
        OrderDetailsContent(state = OrderDetailsUiState())
    }
}