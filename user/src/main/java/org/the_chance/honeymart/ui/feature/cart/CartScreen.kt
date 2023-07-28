package org.the_chance.honeymart.ui.feature.cart

import SwipeBackGround
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.cart.Composeables.CartCardView
import org.the_chance.honeymart.ui.feature.cart.Composeables.CartItem
import org.the_chance.honeymart.ui.feature.cart.Composeables.ErrorPlaceHolder
import org.the_chance.honeymart.ui.feature.cart.Composeables.Loading
import org.the_chance.honeymart.ui.feature.cart.screen.BottomSheetCompleteOrderContent
import org.the_chance.honeymart.ui.feature.uistate.CartUiState
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.user.R

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onClickButtonOrderDetails: () -> Unit = {},
    onClickButtonDiscover: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    CartContent(
        state = state,
        cartInteractionListener = viewModel,
        onClickButtonOrderDetails = onClickButtonOrderDetails,
        onClickButtonDiscover = onClickButtonDiscover
    )
}

@Composable
fun CartContent(
    state: CartUiState,
    cartInteractionListener: CartInteractionListener,
    onClickButtonOrderDetails: () -> Unit = {},
    onClickButtonDiscover: () -> Unit = {}
) {

        when {
            state.isLoading -> Loading()
            state.isError -> ErrorPlaceHolder()
            state.products.isEmpty() -> BottomSheetCompleteOrderContent(
                state = state,
                onClick = onClickButtonOrderDetails,
                onClickButtonDiscover = onClickButtonDiscover
            )
            else -> CartSuccessScreen(
                state = state,
                cartInteractionListener = cartInteractionListener
            )
        }
    }


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun CartSuccessScreen(
    state: CartUiState,
    cartInteractionListener: CartInteractionListener
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        LazyColumn(
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
                            }
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
        CartCardView(totalPrice = state.total.toString())
    }
}

