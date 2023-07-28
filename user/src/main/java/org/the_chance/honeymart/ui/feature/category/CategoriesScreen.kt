package org.the_chance.honeymart.ui.feature.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.cart.Composeables.ErrorPlaceHolder
import org.the_chance.honeymart.ui.feature.cart.Composeables.Loading

/**
 * Created by Aziza Helmy on 7/27/2023.
 */
@Composable
fun CategoriesScreen(viewModel: CategoryViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    CategoryContent(state, viewModel)
}

@Composable
fun CategoryContent(state: CategoriesUiState, listener: CategoryInteractionListener) {
    when {
        state.isLoading -> Loading()
        state.isError -> ErrorPlaceHolder()
        else ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(state.categories) { index, item ->
                    CategoryItem(state = item, onCategoryClicked = {})
                }
            }
    }
}


@Composable
@Preview
fun PreviewCategoryScreen() {
    CategoriesScreen()
}