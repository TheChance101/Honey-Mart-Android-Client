package org.the_chance.honeymart.ui.feature.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyProductPlaceholder
import org.the_chance.honeymart.ui.composables.ProductCard
import org.the_chance.honeymart.ui.feature.authentication.navigateToAuth
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.CategoryItem
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun ProductsScreen(
    viewModel: ProductViewModel = hiltViewModel(),
) {
    val navController = LocalNavigationProvider.current
    val state by viewModel.state.collectAsState()

    ProductsContent(
        state = state,
        productInteractionListener = viewModel,
        navigateToProductScreen =
        {productId -> navController.navigateToProductDetailsScreen(productId)},
        navigateToAuth = { navController.navigateToAuth() }
    )
}


@Composable
private fun ProductsContent(
    state: ProductsUiState,
    productInteractionListener: ProductInteractionListener,
    navigateToProductScreen: (productId: Long) -> Unit,
    navigateToAuth: () -> Unit,
) {
    AppBarScaffold {
        Loading(state.isLoadingCategory)

        ConnectionErrorPlaceholder(state.isError, productInteractionListener::resetNavigation)

        ContentVisibility(state = !state.isLoadingCategory && !state.isError) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(
                            start = MaterialTheme.dimens.space16,
                            end = MaterialTheme.dimens.space16
                        ),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space12)
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = MaterialTheme.dimens.space24,
                            end = MaterialTheme.dimens.space12,
                        ),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
                    ) {
                        items(state.categories.size) { index ->
                            val category = state.categories[index]
                            CategoryItem(
                                iconPainter = painterResource(id = R.drawable.ic_bed),
                                categoryName = category.categoryName,
                                isSelected = category.isCategorySelected,
                                onClick = {
                                    productInteractionListener.onClickCategory(category.categoryId)
                                }
                            )
                        }
                    }
                    EmptyProductPlaceholder(state.isEmptyProducts)
                    Loading(state.isLoadingProduct)
                    AnimatedVisibility(
                        visible = !state.isLoadingProduct,
                        enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
                        exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                top = MaterialTheme.dimens.space24,
                                bottom = MaterialTheme.dimens.space8,
                            ),
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                            state = rememberLazyListState()
                        ) {
                            items(state.products.size) { index ->
                                val product = state.products[index]
                                ProductCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    imageUrl = product.productImages.firstOrNull() ?: "",
                                    productName = product.productName,
                                    productPrice = product.productPrice.toString(),
                                    secondaryText = product.productDescription,
                                    isFavoriteIconClicked = product.isFavorite,
                                    onClickCard = {
                                        productInteractionListener.onClickProduct(product.productId)
                                    },
                                    onClickFavorite = {
                                        productInteractionListener.onClickFavIcon(product.productId)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        LaunchedEffect(key1 = state.navigateToProductDetailsState.isNavigate) {
            if (state.navigateToProductDetailsState.isNavigate) {
                navigateToProductScreen(state.navigateToProductDetailsState.id)
                productInteractionListener.resetNavigation()
            }
        }
        LaunchedEffect(key1 = state.navigateToAuthGraph) {
            if (state.navigateToAuthGraph.isNavigate) {
                navigateToAuth()
                productInteractionListener.resetNavigation()
            }
        }
    }
}



