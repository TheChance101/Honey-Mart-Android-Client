package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.Visibility
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CategoryItems(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
) {
    AnimatedVisibility(visible = state.categories.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.space12)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(140.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            ) {
                items(count = state.categories.size) { index ->
                    CategoryItem(
                        categoryName = state.categories[index].categoryName,
                        onClick = {
                            listener.onClickCategory(state.categories[index].categoryId)
                        },
                        icon = categoryIcons[state.categories[index]
                            .categoryIconUIState.categoryIconId]
                            ?: R.drawable.icon_category,
                        isSelected = state.categories[index].isCategorySelected
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

}