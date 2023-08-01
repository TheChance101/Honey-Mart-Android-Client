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
import org.the_chance.honeymart.ui.feature.market.navigateToMarketScreen
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honeymart.ui.feature.uistate.WishListUiState
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honymart.ui.composables.ItemFavorite
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
        state = state,
        wishListInteractionListener = navController::navigateToProductDetailsScreen,
        onClickDesCover = {
            navController.navigateToMarketScreen()
        },
        onClickIconFavorite = viewModel::onClickFavoriteIcon,
        onClickTryAgain = { viewModel.getWishListProducts() }
    )

}

@Composable
private fun WishListContent(
    state: WishListUiState,
    wishListInteractionListener: (ProductId: Long) -> Unit,
    onClickDesCover: () -> Unit,
    onClickIconFavorite: (ProductId: Long) -> Unit,
    onClickTryAgain: () -> Unit,
) {
    AppBarScaffold {
        ConnectionErrorPlaceholder(state = state.isError, onClickTryAgain = onClickTryAgain)

        EmptyOrdersPlaceholder(
            state = state.products.isEmpty() && !state.isLoading,
            image = R.drawable.placeholder_wish_list,
            title = stringResource(R.string.your_wish_list_is_empty),
            subtitle = stringResource(R.string.subtitle_placeholder_wishList),
            onClickDiscoverMarkets = onClickDesCover
        )

        ContentVisibility(state = state.products.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(MaterialTheme.dimens.space16),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                    content = {
                        items(state.products) { productState ->
                            ItemFavorite(
                                imageUrlMarket = productState.productImages!![0],
                                name = productState.productName!!,
                                price = "${productState.productPrice}",
                                description = "${productState.description} ",
                                productId = productState.productId!!,
                                onClickProduct = { wishListInteractionListener(productState.productId) },
                                onClickFavoriteIcon = { onClickIconFavorite(productState.productId) }

                            )
                        }
                    })
            }
        }
        Loading(state = state.isLoading)
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewWishListScreen() {
    WishListScreen()

}