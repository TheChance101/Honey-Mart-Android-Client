package org.the_chance.honeymart.ui.feature.wishlist


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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honeymart.ui.feature.wishlist.composable.ItemFavorite
import org.the_chance.honeymart.util.formatCurrencyWithNearestFraction
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun WishListScreen(
    viewModel: WishListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = LocalNavigationProvider.current

    LaunchedEffect(lifecycleOwner) {
        viewModel.getWishListProducts()
    }

    WishListContent(
        listener = viewModel,
        state = state,
        wishListInteractionListener = navController::navigateToProductDetailsScreen
    )

}

@Composable
private fun WishListContent(
    listener: WishListInteractionListener,
    state: WishListUiState,
    wishListInteractionListener: (ProductId: Long) -> Unit,
) {

    AppBarScaffold {
        Loading(state = state.isLoading && state.products.isEmpty())

        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = listener::getWishListProducts
        )

        EmptyOrdersPlaceholder(
            state = state.products.isEmpty() && !state.isError && !state.isLoading,
            image = R.drawable.placeholder_wish_list,
            title = stringResource(R.string.your_wish_list_is_empty),
            subtitle = stringResource(R.string.subtitle_placeholder_wishList),
            onClickDiscoverMarkets = listener::onClickDiscoverButton
        )

        ContentVisibility(state = state.products.isNotEmpty() && !state.isError) {
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
                                onClickProduct =
                                { wishListInteractionListener(productState.productId) },
                                onClickFavoriteIcon = { listener.onClickFavoriteIcon(productState.productId) },

                            )
                        }
                    }
            }
        }

        Loading(state = state.isLoading && state.products.isNotEmpty())

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewWishListScreen() {
    WishListScreen()

}