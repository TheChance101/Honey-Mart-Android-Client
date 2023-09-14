package org.the_chance.honeymart.ui.feature.order_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.composables.NavigationHandler
import org.the_chance.honeymart.ui.composables.OrderDetailsCard
import org.the_chance.honeymart.ui.feature.order_details.composables.AddReviewBottomSheet
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honymart.ui.composables.HoneyOutlineText
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                is OrderDetailsUiEffect.ClickProductEffect -> navController.navigateToProductDetailsScreen(
                    effect.productId
                )

                is OrderDetailsUiEffect.ShowToastEffect -> {
                    Toast.makeText(navController.context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    )
    OrderDetailsContent(state = state, listener = viewModel)
}

@Composable
private fun OrderDetailsContent(
    listener: OrderDetailsInteractionListener,
    state: OrderDetailsUiState,
) {
    HoneyAppBarScaffold(
        bottomBar = {
            Box(modifier = Modifier.shadow(elevation = MaterialTheme.dimens.space8)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onTertiary)
                        .padding(
                            horizontal = MaterialTheme.dimens.space16,
                            vertical = MaterialTheme.dimens.space8
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = state.orderDetails.totalPriceCurrency,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = stringResource(id = R.string.total_price),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.displaySmall,
                        )
                    }
                    HoneyOutlineText(text = state.orderDetails.stateText)
                }
            }
        },
    ) {
        Loading(state = state.isProductsLoading)
        ContentVisibility(state = !state.isProductsLoading) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentPadding = PaddingValues(MaterialTheme.dimens.space16),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                    content = {
                        items(items = state.products) { itemOrderDetails ->
                            OrderDetailsCard(
                                imageUrl = itemOrderDetails.imageUrl,
                                orderName = itemOrderDetails.name,
                                orderPrice = itemOrderDetails.priceCurrency,
                                orderCount = "${itemOrderDetails.count}",
                                orderId = itemOrderDetails.id,
                                onClickCard = listener::onClickOrder,
                                onClickAddReview = { listener.onClickAddReview(itemOrderDetails.id) },
                                isAddReviewVisible = state.orderDetails.isAddReviewVisible,
                            )
                        }
                    }
                )
            }
            AddReviewBottomSheet(
                state = state.addReviewBottomSheetUiState,
                onClickSubmit = listener::onClickSubmitReview,
                onDismiss = listener::onDismissAddReviewSheet,
                onRatingChange = listener::onRatingChange,
                onReviewChange = listener::onReviewChange,
            )
        }
    }
}

