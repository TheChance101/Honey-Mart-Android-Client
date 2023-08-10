package org.the_chance.honeymart.ui.category

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
import org.the_chance.honeymart.ui.features.category.categories.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.categories.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.categories.CategoryItem
import org.the_chance.honeymart.ui.features.products.ProductsViewModel
import org.the_chance.honymart.ui.theme.dimens

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
@Composable
fun CategoriesScreen(productViewModel: ProductsViewModel = hiltViewModel()) {

    val state by productViewModel.state.collectAsState()
}

@Composable
fun CategoriesContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
) {
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


@Preview
@Composable
fun PreviewCategoriesScreen() {
    CategoriesScreen()
}