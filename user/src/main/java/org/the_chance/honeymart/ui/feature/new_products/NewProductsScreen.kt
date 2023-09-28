package org.the_chance.honeymart.ui.feature.new_products

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.composables.ProductItem
import org.the_chance.honeymart.ui.feature.authentication.signup.authentication.navigateToAuthScreen
import org.the_chance.honeymart.ui.feature.home.formatCurrencyWithNearestFraction
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun NewProductsScreen(
    viewModel: NewProductsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val navController = LocalNavigationProvider.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                RecentProductUiEffect.UnAuthorizedUserEffect -> navController.navigateToAuthScreen()
                is RecentProductUiEffect.ClickProductEffect ->
                    navController.navigateToProductDetailsScreen(it.productId)
            }
        }
    }
    NewProductsContent(
        recentProductInteractionListener = viewModel,
        state = state,
        onClickRecentProduct = viewModel::onClickProductItem,
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewProductsContent(
    recentProductInteractionListener: RecentProductListener,
    state: RecentProductsUiState,
    onClickRecentProduct: (Long) -> Unit,
) {
    HoneyAppBarScaffold {

        Loading(state = state.recentProducts.isEmpty() && state.isLoading)
        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = recentProductInteractionListener::getRecentProducts,
        )
        ContentVisibility(state = state.recentProducts.isNotEmpty() && !state.isError) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(MaterialTheme.dimens.space16),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
                ) {
                    items(state.recentProducts) { recentProduct ->
                        ProductItem(
                            modifier = Modifier.animateItemPlacement(),
                            productName = recentProduct.productName,
                            productPrice = recentProduct.price.formatCurrencyWithNearestFraction(),
                            imageUrl = recentProduct.productImage,
                            onClick = { onClickRecentProduct(recentProduct.productId) }
                        )
                    }
                }

            }
            Loading(state = state.recentProducts.isNotEmpty() && state.isLoading)
        }
    }
}

