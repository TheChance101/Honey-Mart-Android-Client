package org.the_chance.honeymart.ui.feature.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.feature.cart.composables.BottomSheetCompleteOrderContent
import org.the_chance.honeymart.ui.feature.cart.composables.CartSuccessScreen
import org.the_chance.honeymart.ui.feature.home.navigateToHomeScreen
import org.the_chance.honeymart.ui.feature.orders.navigateToOrderScreen
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
) {
    val navController = LocalNavigationProvider.current
    val state by viewModel.state.collectAsState()


    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                CartUiEffect.ClickDiscoverEffect -> navController.navigateToHomeScreen()
                CartUiEffect.ClickViewOrdersEffect -> navController.navigateToOrderScreen()
            }
        }
    }

    LaunchedEffect(key1 = true) {
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
        Loading(state.unpopulatedLoading())
        ConnectionErrorPlaceholder(
            state = state.errorPlaceHolderCondition(),
            onClickTryAgain = cartInteractionListener::getChosenCartProducts
        )
        EmptyOrdersPlaceholder(
            state = state.placeHolderCondition(),
            image = R.drawable.placeholder_order,
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
            Loading(state = state.populatedLoading())
        }
    }
}



