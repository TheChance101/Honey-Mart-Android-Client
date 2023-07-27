package org.the_chance.honeymart.ui.feature.product

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import org.the_chance.honymart.ui.composables.EmptyProductScaffold
import org.the_chance.honymart.ui.composables.ErrorScaffold
import org.the_chance.honymart.ui.composables.LottieLoadingAnimation
import org.the_chance.honymart.ui.composables.ProductCard
import org.the_chance.honymart.ui.composables.SideBarItem


@Composable
fun ProductsScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ProductsContent(state = state, viewModel, viewModel)
}

@SuppressLint("SuspiciousIndentation")
@Composable
private fun ProductsContent(
    state: ProductsUiState,
    productInteractionListener: ProductInteractionListener,
    categoryProductInteractionListener: CategoryProductInteractionListener
) {
    if (state.isLoadingCategory || state.isLoadingProduct) {
        LottieLoadingAnimation()
    } else if (state.isError) {
        ErrorScaffold()
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(top = 24.dp, end = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.categories.size) { index ->
                        val category = state.categories[index]
                        SideBarItem(
                            icon = R.drawable.ic_bed,
                            categoryName = category.categoryName,
                            isSelected = category.isCategorySelected,
                            onClick = {
                                categoryProductInteractionListener.onClickCategory(category.categoryId)
                            }
                        )
                    }
                }
                if (state.isEmptyProducts) {
                    EmptyProductScaffold()
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(top = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
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
}


