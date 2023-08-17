package org.the_chance.honeymart.ui.features.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.addCategory.AddCategoryContent
import org.the_chance.honeymart.ui.addCategory.categoryIcons
import org.the_chance.honeymart.ui.addCategory.composable.CategoryItem
import org.the_chance.honeymart.ui.addCategory.composable.EmptyCategory
import org.the_chance.honeymart.ui.addCategory.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.products.ProductsOnCategory
import org.the_chance.honeymart.ui.features.update_category.UpdateCategoryContent
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.SnackBarWithDuration
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        HoneyMartTitle()
        Loading(state = state.isLoading && state.categories.isEmpty())
        AnimatedVisibility(visible = state.categories.isEmpty() && !state.showAddCategory) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                EmptyCategory(
                    state = state.categories.isEmpty() && !state.isLoading && !state.isError,
                    onClick = { listener.resetShowState(Visibility.ADD_CATEGORY) }
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
                    onClick = { listener.resetShowState(Visibility.ADD_CATEGORY) }
                )
                AnimatedVisibility(
                    visible = state.categories.isNotEmpty(),
                ) {
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
                                        listener.resetShowState(Visibility.UPDATE_CATEGORY)
                                    },
                                    icon = categoryIcons[state.categories[index].categoryImageId]
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                AddCategoryContent(
                    listener = listener, state = state,
                )
                AnimatedVisibility(visible = state.products.isNotEmpty() && !state.isLoading && !state.showUpdateCategory) {
                    ProductsOnCategory(state = state)
                }

                UpdateCategoryContent(state = state, listener = listener)
            }
        }
    }
    AnimatedVisibility(
        visible = state.snackBar.isShow,
        enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
    ) {
        SnackBarWithDuration(
            message = state.snackBar.message,
            onDismiss = listener::resetSnackBarState,
            undoAction = {},
            text = ""
        )
    }
    Loading(state = state.isLoading && state.categories.isNotEmpty())
}
