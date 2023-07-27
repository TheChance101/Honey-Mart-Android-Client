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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.uistate.WishListUiState
import org.the_chance.honeymart.ui.feature.wishlist.compose.LoadingAnimation
import org.the_chance.honeymart.ui.feature.wishlist.compose.NoConnectionError
import org.the_chance.honeymart.ui.feature.wishlist.compose.PlaceHolderWishList
import org.the_chance.honymart.ui.composables.ItemFavorite
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun WishListScreen(
    viewModel: WishListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val lifecycleOwner = LocalLifecycleOwner.current

    when {
//        state.isLoading -> LoadingAnimation()
        state.isError -> NoConnectionError{viewModel.getWishListProducts()}
        state.products.isEmpty() -> PlaceHolderWishList { viewModel.onClickDiscoverButton() }
        else -> {LaunchedEffect(lifecycleOwner) {
            viewModel.getWishListProducts()
        }

        }
    }
    WishListContent(
        state = state,
        onClickFavoriteIcon = viewModel::onClickFavoriteIcon,
        onClickProduct = viewModel::onClickProduct
    )

}

@Composable
private fun WishListContent(
    state: WishListUiState,
    onClickFavoriteIcon: (Long) -> Unit,
    onClickProduct: (Long) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if(state.isLoading){
            LoadingAnimation()
        }
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
                        onClickProduct = { onClickProduct(productState.productId!!) },
                        onClickFavoriteIcon = { onClickFavoriteIcon(productState.productId!!) }
                    )
                }


            })
    }
//    if (state.isError) {
//        NoConnectionError()
//    } else if (state.isLoading) {
//        Image(
//            painter = rememberAsyncImagePainter(model = org.the_chance.design_system.R.raw.loading),
//            contentDescription = ""
//        )
//    } else if (state.products.isEmpty()) {
//        PlaceHolderWishList(vi)
//    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewWishListScreen() {
    WishListScreen()

}