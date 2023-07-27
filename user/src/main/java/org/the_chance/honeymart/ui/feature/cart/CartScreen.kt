package org.the_chance.honeymart.ui.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import org.the_chance.honeymart.ui.feature.cart.Composeables.CartCardView
import org.the_chance.honeymart.ui.feature.cart.Composeables.CartItem
import org.the_chance.honeymart.ui.feature.cart.Composeables.Loading
import org.the_chance.honeymart.ui.feature.cart.Composeables.NoConnectionLayout
import org.the_chance.honeymart.ui.feature.cart.Composeables.SwipeDelete
import org.the_chance.honeymart.ui.feature.cart.screen.BottomSheetCompleteOrderContent
import org.the_chance.honeymart.ui.feature.uistate.CartUiState

@Composable
fun cartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onClickButtonOrderDetails: () -> Unit ={},
    onClickButtonDiscover : () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> Loading()
        state.isError -> NoConnectionLayout()
        state.products.isEmpty() -> BottomSheetCompleteOrderContent(
            state = state,
            onClick = onClickButtonOrderDetails,
            onClickButtonDiscover = onClickButtonDiscover
        )
        else -> CartContent(
            state = state,
            onClickPlusOrder = {
                viewModel.onClickAddCountProductInCart(it ?: 0)
            },
            onClickMinusOrder = {
                viewModel.onClickMinusCountProductInCart(it ?: 0)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartUiState,
    onClickPlusOrder: (Long?) -> Unit = {},
    onClickMinusOrder: (Long?) -> Unit = {}
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.products.size)
                {index ->
                    val dismissState = rememberDismissState()
                    SwipeToDismiss(
                        state = dismissState,
                        background = { SwipeDelete(state = dismissState) },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissContent = {
                            CartItem(
                                state.products[index],
                                onClickMinus = { onClickMinusOrder(it) },
                                onClickPlus = { onClickPlusOrder(it) }
                            )
                        })
                }
            }
            CartCardView(
                totalPrice = state.total.toString()
            )

        }
        //CartPlaceholder()
        //NoConnectionLayout()
    }
}