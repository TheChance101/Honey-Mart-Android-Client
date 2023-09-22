package org.the_chance.honeymart.ui.features.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.components.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.category.composable.EmptyCategory
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.category.content.AddCategoryContent
import org.the_chance.honeymart.ui.features.category.content.AddProductContent
import org.the_chance.honeymart.ui.features.category.content.CategoryItemsContent
import org.the_chance.honeymart.ui.features.category.content.CategoryProductsContent
import org.the_chance.honeymart.ui.features.category.content.ProductDetailsContent
import org.the_chance.honeymart.ui.features.category.content.UpdateCategoryContent
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.SnackBarWithDuration
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CategoriesScreen(categoriesViewModel: CategoriesViewModel = hiltViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by categoriesViewModel.state.collectAsState()

    CategoriesContent(
        state,
        categoriesViewModel,
        categoriesViewModel::onChangeReviews
    )
    LaunchedEffect(lifecycleOwner) {
        categoriesViewModel.getAllCategory()
        categoriesViewModel.resetStateScreen()
    }
}

@Composable
fun CategoriesContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
    onChangeReviews: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HoneyMartTitle()
        Loading(state = state.showLoadingWhenCategoriesIsEmpty())
        ContentVisibility(state = state.emptyCategoryPlaceHolder()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                EmptyCategory(
                    state = state.placeHolderCondition(),
                    onClick = { listener.resetShowState(Visibility.ADD_CATEGORY) }
                )
            }
        }
        ContentVisibility(state = !state.isError) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                Loading(
                    state = state.isLoading && state.categories.isNotEmpty()
                            && !state.showScreenState.showAddCategory
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    EmptyPlaceholder(
                        state = state.placeHolderCondition(), emptyObjectName = "Category"
                    )

                    ContentVisibility(
                        state = state.categories.isNotEmpty() && state.showScreenState.showFab
                    ) {
                        CategoryItemsContent(state = state, listener = listener)
                    }

                    ContentVisibility(state = state.showCategoryProductsInCategory()) {
                        CategoryProductsContent(
                            state = state,
                            listener = listener,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    ContentVisibility(
                        state = state.showScreenState.showAddCategory
                                && state.showScreenState.showFab
                    ) {
                        AddCategoryContent(listener = listener, state = state)
                    }

                    ContentVisibility(state = state.showCategoryProductsInProduct()) {
                        CategoryProductsContent(
                            state = state,
                            listener = listener,
                        )
                    }

                    ContentVisibility(state = state.showScreenState.showUpdateCategory) {
                        UpdateCategoryContent(state = state, listener = listener)
                    }

                    ContentVisibility(state = state.showAddProductContent()) {
                        AddProductContent(state = state, listener = listener)
                    }

                    ContentVisibility(state = state.showProductDetailsContent()) {
                        ProductDetailsContent(
                            titleScreen = stringResource(id = R.string.product_details),
                            confirmButton = stringResource(id = R.string.update),
                            cancelButton = stringResource(id = R.string.delete),
                            state = state,
                            listener = listener,
                            onClickConfirm = { listener.onClickUpdateProductDetails() },
                            onClickCancel = { listener.resetShowState(Visibility.DELETE_PRODUCT) },
                            onChangeReviews = { listener.onScrollDown() },
                        )
                    }
                    ContentVisibility(state = state.showProductUpdateContent()) {
                        ProductDetailsContent(
                            titleScreen = stringResource(id = R.string.update_product),
                            confirmButton = stringResource(id = R.string.save),
                            cancelButton = stringResource(id = R.string.cancel),
                            state = state,
                            listener = listener,
                            onClickConfirm = { listener.updateProductDetails(state) },
                            onClickCancel = { listener.onClickCancel() },
                            onChangeReviews = { onChangeReviews(it) },
                        )
                    }
                }
            }
        }
    }
    ContentVisibility(
        state = state.snackBar.isShow
    ) {
        SnackBarWithDuration(
            message = stringResource(R.string.add_new_category_success),
            onDismiss = listener::resetSnackBarState,
            undoAction = listener::resetSnackBarState,
            text = stringResource(R.string.dismiss)
        )
    }
    ConnectionErrorPlaceholder(
        state = state.errorPlaceHolderCondition(),
        onClickTryAgain = listener::getAllCategory
    )

    ContentVisibility(state = state.showScreenState.showDialog) {
        CustomAlertDialog(
            message = stringResource(R.string.you_delete_a_categories) +
                    stringResource(R.string.are_you_sure),
            onConfirm = {
                listener.deleteCategory(state.newCategory.categoryId)
                listener.resetShowState(Visibility.DELETE_CATEGORY)
            },

            onCancel = { listener.resetShowState(Visibility.DELETE_CATEGORY) },
            onDismissRequest = { listener.resetShowState(Visibility.DELETE_CATEGORY) }
        )
    }

    ContentVisibility(state = state.showScreenState.showDeleteDialog) {
        CustomAlertDialog(
            message = stringResource(R.string.you_delete_a_product)
                    + stringResource(R.string.are_you_sure),
            onConfirm = {
                listener.deleteProductById(state.newProducts.id)
                listener.resetShowState(Visibility.DELETE_PRODUCT)
            },
            onCancel = { listener.resetShowState(Visibility.DELETE_PRODUCT) },
            onDismissRequest = { listener.resetShowState(Visibility.DELETE_PRODUCT) }
        )
    }
}