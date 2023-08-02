package org.the_chance.honeymart.ui.feature.cart

import SwipeBackGround
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.cart.composables.CartCardView
import org.the_chance.honeymart.ui.feature.cart.composables.CartItem
import org.the_chance.honeymart.ui.feature.cart.composables.CartPlaceholder
import org.the_chance.honeymart.ui.feature.cart.screen.BottomSheetCompleteOrderContent
import org.the_chance.honeymart.ui.feature.market.navigateToMarketScreen
import org.the_chance.honeymart.ui.feature.orders.navigateToOrderScreen
import org.the_chance.honeymart.ui.feature.uistate.CartUiState
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.user.R

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
) {
    val navController = LocalNavigationProvider.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(lifecycleOwner) {
        viewModel.getChosenCartProducts()
    }

    CartContent(
        state = state,
        cartInteractionListener = viewModel,
        onClickButtonOrderDetails = { navController.navigateToOrderScreen() },
        onClickButtonDiscover = { navController.navigateToMarketScreen() }
    )
}

@Composable
fun CartContent(
    state: CartUiState,
    cartInteractionListener: CartInteractionListener,
    onClickButtonOrderDetails: () -> Unit = {},
    onClickButtonDiscover: () -> Unit = {},
) {
    AppBarScaffold {
        Loading((state.isLoading && state.products.isEmpty()))

        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = cartInteractionListener::getChosenCartProducts
        )
        ContentVisibility(state = state.products.isEmpty() && !state.isError && !state.isLoading)
        {
            CartPlaceholder(onClickButtonDiscover = onClickButtonDiscover)
        }

        BottomSheetCompleteOrderContent(
            state = state.bottomSheetIsDisplayed,
            onClick = onClickButtonOrderDetails,
            onClickButtonDiscover = onClickButtonDiscover
        )

        ContentVisibility(state = state.products.isNotEmpty() && !state.isError) {
            CartSuccessScreen(
                state = state,
                cartInteractionListener = cartInteractionListener
            )
        }
        Loading(state = state.isLoading && state.products.isNotEmpty())

    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun CartSuccessScreen(
    state: CartUiState,
    cartInteractionListener: CartInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(state.products) { index, product ->
                var showDialog by remember { mutableStateOf(false) }
                val dismissState = rememberDismissState(
                    confirmValueChange = { it == DismissValue.DismissedToStart })

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = { SwipeBackGround() },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {
                        CartItem(
                            product = product,
                            onClickMinus = {
                                cartInteractionListener.onClickMinusCountProductInCart(
                                    productId = product.productId!!
                                )
                            },
                            onClickPlus = {
                                cartInteractionListener.onClickAddCountProductInCart(
                                    productId = product.productId!!
                                )
                            },
                            isLoading = state.isLoading
                        )
                    }
                )
                if (showDialog) {
                    CustomAlertDialog(
                        message = stringResource(R.string.you_re_delete_this_product_are_you_sure),
                        onConfirm = {
                            cartInteractionListener.deleteCart(index.toLong())
                            showDialog = false
                        },
                        onCancel = { showDialog = false },
                        onDismissRequest = { showDialog = false }
                    )
                }

                LaunchedEffect(showDialog) {
                    if (!showDialog) {
                        dismissState.reset()
                    }
                }

                LaunchedEffect(dismissState.dismissDirection) {
                    if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                        showDialog = true
                    }
                }
            }
        }

        CartCardView(totalPrice = state.total.toString(), isLoading = state.isLoading)
    }

}

