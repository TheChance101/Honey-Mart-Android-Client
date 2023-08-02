package org.the_chance.honeymart.ui.feature.category


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.product.navigateToProductScreen
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CategoriesScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    CategoryContent(
        state,
        listener = viewModel,
        onClickCategory = navController::navigateToProductScreen
    )
}

@Composable
fun CategoryContent(
    state: CategoriesUiState,
    listener: CategoryInteractionListener,
    onClickCategory: (categoryId: Long, marketId: Long, position: Int) -> Unit,
) {
    AppBarScaffold {
        Loading(state.isLoading)

        ConnectionErrorPlaceholder(state.isError, listener::onGetData)

        ContentVisibility(state = !state.isLoading && !state.isError) {
            LazyVerticalGrid(
                state = rememberLazyGridState(),
                columns = GridCells.Fixed(count = 3),
                contentPadding = PaddingValues(MaterialTheme.dimens.space16),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                itemsIndexed(state.categories) { index, item ->
                    CategoryItem(
                        state = item,
                        onCategoryClicked = onClickCategory,
                        categoryId = item.categoryId,
                        marketId = state.marketId,
                        position = index,
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun PreviewCategoryScreen() {
    CategoriesScreen()
}