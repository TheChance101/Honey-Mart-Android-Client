package org.the_chance.honeymart.ui.features.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.addCategory.AddCategoryContent
import org.the_chance.honeymart.ui.addCategory.composable.EmptyCategory
import org.the_chance.honeymart.ui.features.category.categories.CategoryItem
import org.the_chance.honymart.ui.theme.dimens

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
@Composable
fun CategoriesScreen(categoriesViewModel: CategoriesViewModel = hiltViewModel()) {

    val state by categoriesViewModel.state.collectAsState()
    CategoriesContent(state, categoriesViewModel)
}

@Composable
fun CategoriesContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
    showAddCategory:Boolean
) {

    Column() {
        EmptyCategory(
            state = state.categories.isEmpty() && !state.isLoading && !state.isError,
            modifier = Modifier.weight(1f),
            onClick = { listener.updateStateToShowAddCategory(true) }
        )
        AddCategoryContent(
            listener = listener, state = state,
            showAddCategory = state.showAddCategory
        )

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
                    position = index,
                )
            }
        }
    }
}