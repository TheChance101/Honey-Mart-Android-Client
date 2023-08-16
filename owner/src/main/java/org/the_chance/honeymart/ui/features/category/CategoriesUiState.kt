package org.the_chance.honeymart.ui.features.category

import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.addCategory.AddCategoryUIState
import org.the_chance.honeymart.ui.addCategory.CategoryImageUIState
import org.the_chance.honeymart.ui.features.products.ProductUiState

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val message: String = "",
    val nameCategory: String = "",
    val position: Int = 0,
    val snackBar: SnackBarState =SnackBarState(),
    val categoryImageId: Int = 0,
    val showAddCategory: Boolean = false,
    val categoryImages: List<CategoryImageUIState> = emptyList(),
    val addCategoryUiState: AddCategoryUIState = AddCategoryUIState(),
    val products: List<ProductUiState> = emptyList(),
    val categories: List<CategoryUiState> = emptyList(),
    val categoryId: Long = 0L,
    val categoryNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
)
data class SnackBarState(
    val isShow: Boolean = false,
    val message: String = "",
)

data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryImageId: Int = 0,
    val isCategorySelected: Boolean = false,
    val categoryIcon: Int = 0,
)
data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val productPrice: String = "0.0",
)

fun CategoryEntity.toCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryImageId = categoryImageId
    )
}

fun CategoriesUiState.showLazyCondition() = !this.isLoading && !this.isError