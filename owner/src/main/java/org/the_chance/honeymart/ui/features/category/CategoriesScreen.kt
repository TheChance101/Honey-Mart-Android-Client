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
import org.the_chance.honeymart.ui.addCategory.AddCategoryContent
import org.the_chance.honeymart.ui.addCategory.composable.EmptyCategory
import org.the_chance.honeymart.ui.addCategory.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.category.categories.CategoryItems
import org.the_chance.honeymart.ui.features.category.categories.ProductsOnCategory
import org.the_chance.honeymart.ui.features.update_category.UpdateCategoryContent
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

                CategoryItems(state = state, listener = listener)

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                AddCategoryContent(
                    listener = listener, state = state,
                )

                AnimatedVisibility(visible = !state.isLoading && !state.showUpdateCategory) {
                    ProductsOnCategory(state = state, listener = listener)
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
    ConnectionErrorPlaceholder(state = state.isError,
        onClickTryAgain =listener::getAllCategory )


        if (state.showDialog) {
            CustomAlertDialog(
                message = stringResource(org.the_chance.owner.R.string.you_delete_a_categories) +
                        "Are you sure?",
                onConfirm = {
                    listener.deleteCategory(state.categoryId)
                    listener.resetShowState(Visibility.DELETE_CATEGORY)
                },

                onCancel = { listener.resetShowState(Visibility.DELETE_CATEGORY) },
                onDismissRequest = { listener.resetShowState(Visibility.DELETE_CATEGORY)}
            )
        }
}

