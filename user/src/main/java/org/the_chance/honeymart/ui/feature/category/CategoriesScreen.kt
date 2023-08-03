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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.category.composables.CategoryItem
import org.the_chance.honeymart.ui.feature.product.navigateToProductScreen
import org.the_chance.honeymart.util.collect
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
    val lifecycleOwner = LocalLifecycleOwner.current

    lifecycleOwner.collect(viewModel.effect) { effect ->
        effect.getContentIfHandled()?.let {
            when (it) {
                is CategoryUiEffect.ClickCategoryEffect -> navController.navigateToProductScreen(
                    it.categoryId,
                    it.marketId,
                    it.position
                )
            }
        }
    }


    CategoryContent(
        state,
        listener = viewModel,

        )
}

@Composable
fun CategoryContent(
    state: CategoriesUiState,
    listener: CategoryInteractionListener,
) {
    AppBarScaffold {
        Loading(state.isLoading)

        ConnectionErrorPlaceholder(state.isError, listener::onGetData)

        ContentVisibility(state.showLazyCondition()) {
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
                        onCategoryClicked = listener::onClickCategory,
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