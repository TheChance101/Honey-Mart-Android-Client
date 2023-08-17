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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.composables.ProductCard
import org.the_chance.honeymart.ui.feature.product_details.navigateToProductDetailsScreen
import org.the_chance.honeymart.util.collect
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
import org.the_chance.honymart.ui.theme.white200

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val navController = LocalNavigationProvider.current
    val lifecycleOwner = LocalLifecycleOwner.current

    lifecycleOwner.collect(viewModel.effect) { effect ->
        effect.getContentIfHandled()?.let {
            when (it) {
                is SearchUiEffect.OnClickProductCard ->
                    navController.navigateToProductDetailsScreen(it.productId)
            }
        }
    }

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
        Loading(state = state.isLoading)
        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = {},
        )
        EmptyOrdersPlaceholder(
            state = state.emptySearchPlaceHolder(),
            image = R.drawable.placeholder_order,
            title = stringResource(R.string.the_search_result_is_empty),
            subtitle = stringResource(R.string.placeholder_subtitle),
            onClickDiscoverMarkets = {},
            visibility = false
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HoneyTextField(
                    modifier = Modifier.fillMaxWidth(3.4f / 4f),
                    text = searchText,
                    hint = "Search",
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
                        white200
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "",
                        tint = if (state.filtering) {
                            white
                        } else {
                            black37
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
                        text = "Sort by price",
                        color = black37,
                        modifier = Modifier.padding(
                            start = MaterialTheme.dimens.space16,
                            bottom = MaterialTheme.dimens.space8,
                            top = MaterialTheme.dimens.space16
                        ),
                        style = Typography.displaySmall
                    )
                    Row(
                        modifier = Modifier.padding(start = MaterialTheme.dimens.space16),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                    ) {
                        CustomChip(
                            state = state.random(),
                            text = "Random",
                            onClick = listener::getAllRandomSearch
                        )
                        CustomChip(
                            state = state.ascending(),
                            text = "Ascending",
                            onClick = listener::getAllAscendingSearch
                        )
                        CustomChip(
                            state = state.descending(),
                            text = "Descending",
                            onClick = listener::getAllDescendingSearch
                        )
                    }
                }
            }
            ContentVisibility(state = state.screenContent()) {
                if (isSearching) {
                    Loading(state = state.isLoading)
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 160.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentPadding = PaddingValues(MaterialTheme.dimens.space16),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                        state = rememberLazyGridState(),
                        content = {
                            items(state.products.size) { itemResult ->
                                val product = state.products[itemResult]
                                ProductCard(
                                    visibility = false,
                                    imageUrl = product.productImages[0],
                                    productName = product.productName,
                                    productPrice = product.productPrice.toString(),
                                    secondaryText = product.marketName,
                                    isFavoriteIconClicked = false,
                                    onClickFavorite = { },
                                    onClickCard = { listener.onClickProduct(product.productId) }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun SearchPrev() {
    SearchScreen()
}