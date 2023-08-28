package org.the_chance.honeymart.ui.feature.cart.composables

import SwipeBackground
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.cart.CartInteractionListener
import org.the_chance.honeymart.ui.feature.cart.CartUiState
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.theme.dimens


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
)
@Composable
fun CartSuccessScreen(
    state: CartUiState,
    cartInteractionListener: CartInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space16
            ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            itemsIndexed(
                items = state.products,
                key = { _, product -> product.productId }) { index, product ->
                var showDialog by remember { mutableStateOf(false) }
                val dismissState = rememberDismissState(
                    confirmValueChange = { it == DismissValue.DismissedToStart })

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = { SwipeBackground() },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {
                        CartItem(
                            product = product,
                            onClickMinus = {
                                cartInteractionListener.onClickMinusCountProductInCart(
                                    productId = product.productId
                                )
                            },
                            onClickPlus = {
                                cartInteractionListener.onClickAddCountProductInCart(
                                    productId = product.productId
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
        CartCardView(
            totalPrice = state.total, isLoading = state.isLoading,
            onClick = cartInteractionListener::onClickOrderNowButton
        )
    }

}