package org.the_chance.honeymart.ui.features.category.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.Visibility
import org.the_chance.honeymart.ui.features.category.composable.AddProductButton
import org.the_chance.honeymart.ui.features.category.composable.DropDownMenuList
import org.the_chance.honeymart.ui.features.category.composable.PagingStateVisibility
import org.the_chance.honeymart.ui.features.category.composable.ProductCard
import org.the_chance.honeymart.ui.features.category.composable.categoryIcons
import org.the_chance.honeymart.ui.util.toCountProductFormat
import org.the_chance.honymart.ui.composables.HoneyOutlineText
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.blackOn60
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CategoryProductsContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
) {
    val products = state.products.collectAsLazyPagingItems()

    Box(contentAlignment = Alignment.BottomEnd) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(
                    top = MaterialTheme.dimens.space24,
                    start = MaterialTheme.dimens.space16,
                    end = MaterialTheme.dimens.space16,
                    bottom = MaterialTheme.dimens.space16,
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (state.categories.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.size(MaterialTheme.dimens.icon48),
                            painter = painterResource(
                                id = categoryIcons[state.categories[state.position]
                                    .categoryIconUIState.categoryIconId]
                                    ?: R.drawable.icon_category
                            ),
                            contentDescription = "category icon",
                            tint = black37
                        )

                        Text(
                            text = state.categories[state.position].categoryName,
                            style = MaterialTheme.typography.bodySmall,
                            color = blackOn60
                        )
                    }

                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HoneyOutlineText(text = products.itemCount.toCountProductFormat())

                    DropDownMenuList(
                        onClickUpdate = { listener.resetShowState(Visibility.UPDATE_CATEGORY) },
                        onClickDelete = { listener.resetShowState(Visibility.DELETE_CATEGORY) }
                    )
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space24)
            ) {
                items(products.itemCount) { index ->
                    products[index]?.let {
                        ProductCard(
                            onClick = { listener.onClickProduct(it.productId) },
                            imageUrl = it.productImage.firstOrNull() ?: "",
                            productName = it.productName,
                            productPrice = it.productPrice,
                            description = it.productDescription
                        )
                    }
                }
                item {
                    PagingStateVisibility(products)
                }
            }
            EmptyPlaceholder(
                state = products.itemCount <= 0 && products.loadState.refresh != LoadState.Loading,
                emptyObjectName = "Product"
            )
        }

        AddProductButton(
            modifier = Modifier.padding(
                bottom = MaterialTheme.dimens.space48,
                end = MaterialTheme.dimens.space48
            ),
            state = state,
            onClick = listener::onClickAddProductButton
        )
    }
}
