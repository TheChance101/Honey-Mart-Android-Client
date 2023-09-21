package org.the_chance.honeymart.ui.feature.product

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import org.the_chance.honeymart.ui.composables.HoneyAppBarScaffold
import org.the_chance.honeymart.ui.composables.PagingLoading
import org.the_chance.honeymart.ui.composables.ProductCard
import org.the_chance.honeymart.ui.feature.authentication.signup.authentication.navigateToAuthScreen
import org.the_chance.honeymart.ui.feature.product.composable.CategoryItem
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.SnackBarWithDuration
import org.the_chance.honymart.ui.composables.categoryIcons
import org.the_chance.honymart.ui.theme.dimens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    viewModel: ProductViewModel = hiltViewModel(),

    ) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    LaunchedEffect(true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProductUiEffect.AddedToWishListEffect -> {
                    viewModel.showSnackBar(effect.message)
                }

                is ProductUiEffect.ClickProductEffect -> navController.navigateToProductDetailsScreen(
                    effect.productId
                )

                ProductUiEffect.UnAuthorizedUserEffect -> navController.navigateToAuthScreen()
            }
        }
    }
    ProductsContent(
        state = state,
        productInteractionListener = viewModel
    )
}

@Composable
private fun ProductsContent(
    state: ProductsUiState,
    productInteractionListener: ProductInteractionListener
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = state.position)

    HoneyAppBarScaffold {
        val products = state.products
        Loading(state.isLoadingCategory)
        ConnectionErrorPlaceholder(state.isError, productInteractionListener::onclickTryAgain)
        ContentVisibility(state = state.contentScreen()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = MaterialTheme.dimens.space16,
                        end = MaterialTheme.dimens.space16
                    ),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space12)
            ) {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(
                        top = MaterialTheme.dimens.space24,
                        bottom = MaterialTheme.dimens.space24,
                        end = MaterialTheme.dimens.space12,
                    ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
                ) {
                    itemsIndexed(
                        state.categories,
                        key = { _, category -> category.categoryId }) { _, category ->
                        CategoryItem(
                            iconPainter = painterResource(
                                id = categoryIcons[category.categoryImageId]
                                    ?: R.drawable.ic_cup_paper
                            ),
                            categoryName = category.categoryName,
                            isSelected = category.isCategorySelected,
                            enable = !state.snackBar.isShow,
                            onClick = { productInteractionListener.onClickCategory(category.categoryId) }
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.contentScreen(),
                    enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
                    exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
                ) {
                    EmptyProductPlaceholder(state.emptyPlaceHolder() && products.isEmpty())
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            top = MaterialTheme.dimens.space24,
                            bottom = MaterialTheme.dimens.space8,
                        ),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    ) {
                        item {
                            Loading(state = state.isLoadingProduct && state.products.isEmpty())
                        }
                        items(products.size) { index ->
                            productInteractionListener.onChangeProductScrollPosition(index)
                            val product = products[index]
                            ProductCard(
                                modifier = Modifier.fillMaxWidth(),
                                imageUrl = product.productImages.firstOrNull() ?: "",
                                productName = product.productName,
                                productPrice = product.productPrice.toString(),
                                secondaryText = product.productDescription,
                                isFavoriteIconClicked = product.isFavorite,
                                onClickCard = {
                                    productInteractionListener.onClickProduct(
                                        product.productId
                                    )
                                },
                                onClickFavorite = {
                                    productInteractionListener.onClickFavIcon(
                                        product.productId
                                    )
                                }
                            )
                        }
                        item {
                            PagingLoading(state = state.isLoadingProduct && state.products.isNotEmpty())
                        }
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
                onDismiss = productInteractionListener::resetSnackBarState,
                undoAction = {
                    productInteractionListener.onClickFavIcon(state.snackBar.productId)
                })
        }
    }
}