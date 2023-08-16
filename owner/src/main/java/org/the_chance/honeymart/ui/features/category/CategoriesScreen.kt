package org.the_chance.honeymart.ui.features.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.addCategory.AddCategoryContent
import org.the_chance.honeymart.ui.addCategory.categoryIcons
import org.the_chance.honeymart.ui.addCategory.composable.CategoryItem
import org.the_chance.honeymart.ui.addCategory.composable.EmptyCategory
import org.the_chance.honeymart.ui.addCategory.composable.HoneyMartTitle
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
) {

    Column() {
        HoneyMartTitle()
        AnimatedVisibility(visible = state.categories.isEmpty() && !state.showAddCategory) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                EmptyCategory(
                    state = state.categories.isEmpty() && !state.isLoading && !state.isError,
                    onClick = { listener.updateStateToShowAddCategory(true) }
                )
            }
        }


            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    EmptyCategory(
                        state = state.categories.isEmpty() && !state.isLoading && !state.isError,
                        onClick = { listener.updateStateToShowAddCategory(true) }
                    )
                    AnimatedVisibility(
                        visible = state.categories.isNotEmpty(),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(5),
                                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space32)
                            ) {
                                items(count = state.categories.size) { index ->
                                    CategoryItem(
                                        categoryName = state.categories[index].categoryName,
                                        onClick = {
                                            listener.onClickCategory(state.categories[index].categoryId)
                                        },
                                        icon = categoryIcons[state.categories[index].categoryIcon]
                                            ?: R.drawable.icon_category,
                                        isSelected = state.categories[index].isCategorySelected
                                    )
                                }
                                item {
                                    CategoryItem(
                                        categoryName = stringResource(id = R.string.add),
                                        onClick = {listener.updateStateToShowAddCategory(true)},
                                        icon = R.drawable.icon_add_to_cart,
                                        isSelected = false
                                    )
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                        AddCategoryContent(
                            listener = listener, state = state,
                        )


                }
            }
        }
    }
