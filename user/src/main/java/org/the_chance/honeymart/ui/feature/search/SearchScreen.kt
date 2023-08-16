package org.the_chance.honeymart.ui.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.composables.ProductCard
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    SearchContent(state = state)
}


@Composable
fun SearchContent(
    state: SearchUiState
) {
    AppBarScaffold {
        Loading(state = state.isLoading)
        ConnectionErrorPlaceholder(
            state = state.isError,
            onClickTryAgain = {}
        )
        EmptyOrdersPlaceholder(
            state = state.emptySearchPlaceHolder(),
            image = R.drawable.placeholder_order,
            title = stringResource(R.string.the_search_result_is_empty),
            subtitle = stringResource(R.string.placeholder_subtitle),
            onClickDiscoverMarkets = {},
            visibility = false
        )
        ContentVisibility(state = state.screenContent()) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                    items(4) { itemResult ->
                        ProductCard(
                            visibility = false,
                            imageUrl = "image_test",
                            productName = "sofa",
                            productPrice = "10,000$",
                            secondaryText = "market name",
                            isFavoriteIconClicked = false,
                            onClickFavorite = { },
                            onClickCard = { }
                        )
                    }
                }
            )
        }
    }
    }
}

@Preview
@Composable
fun SearchPrev() {
    SearchScreen()
}