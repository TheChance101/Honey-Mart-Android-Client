package org.the_chance.honeymart.ui.feature.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.feature.cart.composables.BottomSheetCompleteOrderContent
import org.the_chance.honeymart.ui.feature.cart.composables.CartSuccessScreen
import org.the_chance.honeymart.ui.feature.market.navigateToMarketScreen
import org.the_chance.honeymart.ui.feature.orders.navigateToOrderScreen
import org.the_chance.honeymart.util.collect
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.user.R

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
) {
    val navController = LocalNavigationProvider.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsState()

    lifecycleOwner.collect(viewModel.effect) { effect ->
        effect.getContentIfHandled()?.let {
            when (it) {
                CartUiEffect.ClickDiscoverEffect -> navController.navigateToMarketScreen()
                CartUiEffect.ClickViewOrdersEffect -> navController.navigateToOrderScreen()
            }
        }
    }
    LaunchedEffect(lifecycleOwner) {
        viewModel.getChosenCartProducts()
    }
    CartContent(state = state, cartInteractionListener = viewModel)
}

@Composable
fun CartContent(
    state: CartUiState,
    cartInteractionListener: CartInteractionListener,
) {
    AppBarScaffold {
        Loading(state.unpopulatedLoading() || state.populatedLoading())
        ConnectionErrorPlaceholder(
            state = state.errorPlaceHolderCondition(),
            onClickTryAgain = cartInteractionListener::getChosenCartProducts
        )
        EmptyOrdersPlaceholder(
            state = state.placeHolderCondition(),
            image = org.the_chance.design_system.R.drawable.placeholder_order,
            title = stringResource(R.string.your_cart_list_is_empty),
            subtitle = stringResource(R.string.adding_items_that_catch_your_eye),
            onClickDiscoverMarkets = cartInteractionListener::onClickDiscoverButton
        )

        BottomSheetCompleteOrderContent(
            state = state.bottomSheetIsDisplayed,
            onClick = cartInteractionListener::onClickViewOrders,
        )

        ContentVisibility(state.cartSuccessScreenCondition()) {
            CartSuccessScreen(
                state = state,
                cartInteractionListener = cartInteractionListener
            )
        }
    }
}



