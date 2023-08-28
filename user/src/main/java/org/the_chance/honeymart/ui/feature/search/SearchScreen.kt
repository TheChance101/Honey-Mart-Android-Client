package org.the_chance.honeymart.ui.feature.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyProductPlaceholder
import org.the_chance.honeymart.ui.composables.NavigationHandler
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honeymart.ui.feature.search.composeable.CardSearch
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white
import pagingStateVisibilityGridScope

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = {effect, navController ->
            when (effect) {
                is SearchUiEffect.OnClickProductCard ->
                    navController.navigateToProductDetailsScreen(effect.productId)
            }
        })

    SearchContent(
        state = state,
        searchText = searchText,
        onSearchTextChange = viewModel::onSearchTextChange,
        isSearching = isSearching,
        listener = viewModel
    )
}

@Composable
fun SearchContent(
    state: SearchUiState,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    isSearching: Boolean,
    listener: SearchInteraction,
) {
    AppBarScaffold {
        val products = state.products.collectAsLazyPagingItems()
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = MaterialTheme.dimens.space16,
                        bottom = MaterialTheme.dimens.space16
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HoneyTextField(
                    modifier = Modifier.fillMaxWidth(3.4f / 4f),
                    text = searchText,
                    hint = stringResource(org.the_chance.user.R.string.search),
                    iconPainter = painterResource(id = R.drawable.search),
                    onValueChange = onSearchTextChange,
                    color = black37
                )
                IconButton(
                    size = MaterialTheme.dimens.icon48,
                    onClick = listener::onClickFilter,
                    backgroundColor = if (state.filtering) {
                        primary100
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "",
                        tint = if (state.filtering) {
                            white
                        } else {
                            MaterialTheme.colorScheme.onTertiaryContainer
                        },
                    )
                }
            }
            AnimatedVisibility(
                visible = state.filtering,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)) + slideInVertically(),
                exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutVertically()
            ) {
                Column {
                    Text(
                        text = stringResource(org.the_chance.user.R.string.sort_by_price),
                        color = black37,
                        modifier = Modifier.padding(
                            start = MaterialTheme.dimens.space16,
                            bottom = MaterialTheme.dimens.space8,
                        ),
                        style = Typography.displaySmall
                    )
                    Row(
                        modifier = Modifier.padding(
                            start = MaterialTheme.dimens.space16,
                            bottom = MaterialTheme.dimens.space16
                        ),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                    ) {
                        CustomChip(
                            state = state.random(),
                            text = stringResource(org.the_chance.user.R.string.random),
                            onClick = listener::onClickRandomSearch
                        )
                        CustomChip(
                            state = state.ascending(),
                            text = stringResource(org.the_chance.user.R.string.ascending),
                            onClick = listener::onClickAscendingSearch
                        )
                        CustomChip(
                            state = state.descending(),
                            text = stringResource(org.the_chance.user.R.string.descending),
                            onClick = listener::onClickDescendingSearch
                        )
                    }
                }
            }

            ContentVisibility(state = products.itemCount > 0) {
                if (isSearching) {
                    Loading(state = state.isLoading)
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 160.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .shadow(0.6.dp),
                        contentPadding = PaddingValues(MaterialTheme.dimens.space16),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                        state = rememberLazyGridState(),
                        content = {
                            items(products.itemCount) { index ->
                                val product = products[index]
                                if (product != null) {
                                    CardSearch(
                                        imageUrl = product.productImages.firstOrNull() ?: "",
                                        productName = product.productName,
                                        productPrice = product.productPrice.toString(),
                                        onClickCard = { listener.onClickProduct(product.productId) }
                                    )
                                }
                            }
                            pagingStateVisibilityGridScope(products)
                        }
                    )
                }
            }
        }
        Loading(state = state.isLoading)
        ConnectionErrorPlaceholder(state.isError, listener::onclickTryAgain)
        EmptyProductPlaceholder(products.itemCount == 0 && !state.isError && !state.isLoading)
    }

}


@Preview
@Composable
fun SearchPrev() {
    SearchScreen()
}