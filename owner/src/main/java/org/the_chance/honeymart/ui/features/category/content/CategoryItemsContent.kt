package org.the_chance.honeymart.ui.features.category.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.Visibility
import org.the_chance.honeymart.ui.features.category.composable.CategoryItem
import org.the_chance.honymart.ui.composables.categoryIcons
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CategoryItemsContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(140.dp),
            contentPadding = PaddingValues(bottom = MaterialTheme.dimens.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
        ) {
            items(items = state.categories, key = { it.categoryId }) { category ->
                CategoryItem(
                    categoryName = category.categoryName,
                    onClick = {
                        listener.onClickCategory(category.categoryId)
                    },
                    icon = categoryIcons[category
                        .categoryIconUIState.categoryIconId]
                        ?: R.drawable.icon_category,
                    isSelected = category.isCategorySelected
                )
            }
            item {
                CategoryItem(
                    categoryName = stringResource(id = R.string.add),
                    onClick =
                    { listener.resetShowState(Visibility.ADD_CATEGORY) },
                    icon = R.drawable.icon_add_to_cart,
                    isSelected = false
                )
            }
        }
    }
}