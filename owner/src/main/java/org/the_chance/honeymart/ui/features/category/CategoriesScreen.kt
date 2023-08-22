package org.the_chance.honeymart.ui.features.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.features.add_product.components.AddProductContent
import org.the_chance.honeymart.ui.features.category.composable.AddCategoryContent
import org.the_chance.honeymart.ui.features.category.composable.CategoryItems
import org.the_chance.honeymart.ui.features.category.composable.CategoryProducts
import org.the_chance.honeymart.ui.features.category.composable.EmptyCategory
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.category.composable.UpdateCategoryContent
import org.the_chance.honeymart.ui.features.product_details.composables.ProductDetailsContent
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.SnackBarWithDuration

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
@Composable
fun CategoriesScreen(categoriesViewModel: CategoriesViewModel = hiltViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by categoriesViewModel.state.collectAsState()
    CategoriesContent(state, categoriesViewModel)
    LaunchedEffect(lifecycleOwner) {
        categoriesViewModel.getAllCategory()
    }
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
        AnimatedVisibility(
            visible = state.categories.isEmpty() &&
                    !state.showScreenState.showAddCategory
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                EmptyCategory(
                    state = state.showEmptyPlaceHolder(),
                    onClick = { listener.resetShowState(Visibility.ADD_CATEGORY) }
                )
            }
        }


        Row(modifier = Modifier.fillMaxSize()) {
            Loading(state = state.isLoading && state.categories.isNotEmpty())
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Loading(
                    state = state.isLoading && state.categories.isNotEmpty() &&
                            state.showScreenState.showFab
                )

                EmptyCategory(
                    state = state.categories.isEmpty() && !state.isLoading && !state.isError,
                    onClick = { listener.resetShowState(Visibility.ADD_CATEGORY) }
                )

                CategoryItems(state = state, listener = listener)

                AnimatedVisibility(visible = state.showCategoryProductsInCategory())
                { CategoryProducts(state = state, listener = listener) }

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                AnimatedVisibility(visible = state.showScreenState.showAddCategory)
                {
                    AddCategoryContent(listener = listener, state = state)
                }

                AnimatedVisibility(visible = state.showCategoryProductsInProduct())
                {
                    CategoryProducts(state = state, listener = listener)
                }

                AnimatedVisibility(visible = state.showScreenState.showUpdateCategory)
                {
                    UpdateCategoryContent(state = state, listener = listener)
                }

                AnimatedVisibility(visible = state.showAddProductContent())
                {
                    AddProductContent(state = state, listener = listener)
                }
                AnimatedVisibility(visible = state.showProductDetailsContent())
                {
                    ProductDetailsContent(
                        titleScreen = stringResource(id = org.the_chance.design_system.R.string.product_details),
                        confirmButton = stringResource(id = org.the_chance.design_system.R.string.update),
                        cancelButton = stringResource(id = org.the_chance.design_system.R.string.delete),
                        state = state,
                        listener = listener
                    )
                }
                AnimatedVisibility(visible = state.showProductUpdateContent())
                {


                    ProductDetailsContent(
                        titleScreen = stringResource(id = org.the_chance.design_system.R.string.update_product),
                        confirmButton = stringResource(id = org.the_chance.design_system.R.string.save),
                        cancelButton = stringResource(id = org.the_chance.design_system.R.string.cancel),
                        state = state,
                        listener = listener
                    )
                }
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
    ConnectionErrorPlaceholder(
        state = state.isError,
        onClickTryAgain = listener::getAllCategory
    )


    if (state.showScreenState.showDialog) {
        CustomAlertDialog(
            message = stringResource(org.the_chance.owner.R.string.you_delete_a_categories) +
                    "Are you sure?",
            onConfirm = {
                listener.deleteCategory(state.newCategory.categoryId)
                listener.resetShowState(Visibility.DELETE_CATEGORY)
            },

            onCancel = { listener.resetShowState(Visibility.DELETE_CATEGORY) },
            onDismissRequest = { listener.resetShowState(Visibility.DELETE_CATEGORY) }
        )
    }
}

