package org.the_chance.honeymart.ui.feature.order_details


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsUiState
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.OrderDetailsCard
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val navController = LocalNavigationProvider.current

    ContentVisibility(state = !state.isProductsLoading) {
        OrderDetailsContent(
            state = state,
            onClickItemOrderDetails = navController::navigateToProductDetailsScreen
        )
    }
}

@Composable
private fun OrderDetailsContent(
    state: OrderDetailsUiState,
    onClickItemOrderDetails: (orderId: Long) -> Unit = {},
) {
    AppBarScaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            Loading(state = state.isProductsLoading)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentPadding = PaddingValues(MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                state = rememberLazyGridState(),
                content = {
                    items(state.products) { itemOrderDetails ->
                        OrderDetailsCard(
                            imageUrl = itemOrderDetails.images[0],
                            orderName = itemOrderDetails.name,
                            orderPrice = "${itemOrderDetails.price}",
                            orderCount = "${itemOrderDetails.count}",
                            orderId = itemOrderDetails.id,
                            onClickCard = { onClickItemOrderDetails(itemOrderDetails.id) },
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier.shadow(elevation = 20.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onTertiary)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = MaterialTheme.dimens.space16,
                                vertical = MaterialTheme.dimens.space8
                            )
                    ) {
                        Text(
                            text = "${state.orderDetails.totalPrice}$",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.bodyMedium,
                        )
                        Text(
                            text = stringResource(id = org.the_chance.design_system.R.string.total_price),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = Typography.displaySmall,
                        )
                    }

                    Card(
                        colors = CardDefaults.cardColors(Color.Transparent),
                        modifier = Modifier.padding(
                            top = MaterialTheme.dimens.space16,
                            end = MaterialTheme.dimens.space16,
                        ),
                    ) {
                        Text(
                            text = state.orderDetails.stateText,
                            color = primary100,
                            style = Typography.displayLarge,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(
                                    vertical = MaterialTheme.dimens.space6,
                                    horizontal = MaterialTheme.dimens.space16
                                ),
                        )
                    }
                }
            }
        }
    }
}

