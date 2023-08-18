package org.the_chance.honeymart.ui.feature.wishlist


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.feature.market.navigateToMarketScreen
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honeymart.ui.feature.wishlist.composable.ItemFavorite
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.SnackBarWithDuration
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun WishListScreen(
    viewModel: WishListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val navController = LocalNavigationProvider.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                WishListUiEffect.ClickDiscoverEffect -> navController.navigateToMarketScreen()
                is WishListUiEffect.ClickProductEffect -> navController.navigateToProductDetailsScreen(
                    it.productId
                )

                is WishListUiEffect.DeleteProductFromWishListEffect -> {
                    viewModel.onShowSnackBar(it.message)
                }

            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getWishListProducts()
    }

    WishListContent(
        wishListInteractionListener = viewModel,
        state = state,
    )
}

@Composable
private fun WishListContent(
    wishListInteractionListener: WishListInteractionListener,
    state: WishListUiState,
) {
    HoneyAppBarScaffold {

        Loading(state = state.firstLoading())

        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = wishListInteractionListener::getWishListProducts
        )

        EmptyOrdersPlaceholder(
            state = state.emptyPlaceholder(),
            image = R.drawable.placeholder_wish_list,
            title = stringResource(R.string.your_wish_list_is_empty),
            subtitle = stringResource(R.string.subtitle_placeholder_wishList),
            onClickDiscoverMarkets = wishListInteractionListener::onClickDiscoverButton
        )

        ContentVisibility(state = state.contentScreen()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(MaterialTheme.dimens.space16),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
                ) {
                    items(state.products) { productState ->
                        ItemFavorite(
                            imageUrlMarket = productState.productImages[0],
                            name = productState.productName,
                            price = formatCurrencyWithNearestFraction(productState.productPrice),
                            description = productState.description,
                            productId = productState.productId,
                            onClickProduct = wishListInteractionListener::onClickProduct,
                            onClickFavoriteIcon = {
                                wishListInteractionListener.onClickFavoriteIcon(
                                    productState.productId
                                )
                            },
                            enable = !state.isLoading
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = state.snackBar.isShow,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
            exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            SnackBarWithDuration(message = state.snackBar.message,
                onDismiss = wishListInteractionListener::resetSnackBarState,
                undoAction = {
                    wishListInteractionListener.addProductToWishList(state.snackBar.productId)
                })
        }
        Loading(state = state.loading())
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewWishListScreen() {
    WishListScreen()
}