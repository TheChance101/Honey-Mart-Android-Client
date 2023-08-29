package org.the_chance.honeymart.ui.features.coupons.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.coupons.ProductSearchInteractionListener
import org.the_chance.honeymart.ui.features.coupons.ProductSearchUiState
import org.the_chance.honeymart.ui.features.coupons.composables.ProductCard
import org.the_chance.honeymart.ui.features.coupons.showEmptyPlaceHolder
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductSearchContent(
    state: ProductSearchUiState,
    listener: ProductSearchInteractionListener,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(Color.Transparent)
    ) {
        HoneyTextField(
            modifier = Modifier
                .fillMaxWidth(),
            text = state.searchText,
            hint = stringResource(id = R.string.search),
            iconPainter = painterResource(id = R.drawable.search),
            onValueChange = { listener.onProductSearchTextChange(it) },
        )
        Loading(state = state.isLoading)

        AnimatedVisibility(visible = !state.isLoading) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            ) {
                items(items = state.products, key = { it.id }) { product ->
                    ProductCard(
                        modifier = Modifier.animateItemPlacement(),
                        onClick = { listener.onProductSearchItemClick(product.id) },
                        productName = product.name,
                        productPrice = product.productPriceFormatted,
                        imageUrl = product.imageUrl,
                        description = product.description,
                        isSelected = product.isSelected,
                    )
                }
            }
        }

        EmptyPlaceholder(
            state = state.showEmptyPlaceHolder(),
            emptyObjectName = stringResource(R.string.product)
        )
    }
}