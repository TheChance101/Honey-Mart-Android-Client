package org.the_chance.honeymart.ui.feature.order_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsUiState
import org.the_chance.honymart.ui.composables.OrderDetailsCard
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    OrderDetailsContent(state = state)
}

@Composable
private fun OrderDetailsContent(
    state: OrderDetailsUiState,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            state = rememberLazyGridState(),
            content = {
                items(state.products){itemOrderDetails ->
                    OrderDetailsCard(
                        imageUrl = itemOrderDetails.images!![0],
                        orderName = itemOrderDetails.name!!,
                        orderPrice = "$${itemOrderDetails.price}",
                        orderCount = "x${itemOrderDetails.count}"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        Row(
            Modifier
                .shadow(1.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space8)) {
                Text(
                    text = "$${state.orderDetails.totalPrice}",
                    color = black60,
                    style = Typography.bodyMedium
                )
                Text(
                    text = stringResource(id = org.the_chance.design_system.R.string.total_price),
                    color = black37,
                    style = Typography.displaySmall
                )
            }
            Card(
                shape = RoundedCornerShape(MaterialTheme.dimens.space24),
                colors = CardDefaults.cardColors(white),
                border = BorderStroke(0.dp, primary100) ,
                modifier = Modifier.padding(
                    top = MaterialTheme.dimens.space16,
                    start = 190.dp)
            ) {
                Text(
                    text = "${state.orderDetails.state}",
                    color = primary100,
                    style = Typography.displayLarge,
                    modifier = Modifier
                        .padding(
                            vertical = MaterialTheme.dimens.space4,
                            horizontal = MaterialTheme.dimens.space8),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOrderDetails(){
    OrderDetailsScreen()
}