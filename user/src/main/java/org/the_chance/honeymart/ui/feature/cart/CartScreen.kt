package org.the_chance.honeymart.ui.feature.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.cart.composables.BottomSheetCompleteOrderContent
import org.the_chance.honeymart.ui.feature.cart.composables.CartSuccessScreen
import org.the_chance.honeymart.ui.feature.market.navigateToMarketScreen
import org.the_chance.honeymart.ui.feature.orders.navigateToOrderScreen
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.EmptyOrdersPlaceholder
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
        EmptyOrdersPlaceholder(
            state = state.products.isEmpty() && !state.isError && !state.isLoading,
            image = org.the_chance.design_system.R.drawable.placeholder_order,
            title = stringResource(R.string.your_cart_list_is_empty),
            subtitle = stringResource(R.string.adding_items_that_catch_your_eye),
            onClickDiscoverMarkets = { onClickButtonDiscover() })

        BottomSheetCompleteOrderContent(
            state = state.bottomSheetIsDisplayed,
            onClick = onClickButtonOrderDetails,
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



