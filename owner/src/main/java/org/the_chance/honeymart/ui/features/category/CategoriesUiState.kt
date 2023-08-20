package org.the_chance.honeymart.ui.features.category

import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
// region Ui State
data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val message: String = "",
    val snackBar: SnackBarState = SnackBarState(),
    val products: List<ProductUiState> = emptyList(),
    val categories: List<CategoryUiState> = emptyList(),
    val categoryIcons: List<CategoryIconUIState> = emptyList(),
    val showScreenState: ShowScreenState = ShowScreenState()
)

data class ShowScreenState(
    val showAddCategory: Boolean = false,
    val showUpdateCategory: Boolean = false,
    val showAddProduct: Boolean = false,
    val showProductDetails: Boolean = false,
    val showCategoryProducts: Boolean = false,
    val showDialog: Boolean = false,
)

data class SnackBarState(
    val isShow: Boolean = false,
    val message: String = "",
)

data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryIconUIState: CategoryIconUIState = CategoryIconUIState(),
    val position: Int = 0,
    val categoryNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
)

data class CategoryIconUIState(
    val categoryIconId: Int = 0,
    val icon: Int = 0,
    val isSelected: Boolean = false,
)

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val productPrice: String = "",
    val productsQuantity: String = "",
)

enum class Visibility {
    UPDATE_CATEGORY,
    ADD_CATEGORY,
    DELETE_CATEGORY,
}
// endregion

// region Mapper
fun CategoryEntity.toCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryIconUIState = CategoryIconUIState(categoryIconId = categoryImageId)
    )
}

fun Map<Int, Int>.toCategoryImageUIState(): List<CategoryIconUIState> {
    return map {
        CategoryIconUIState(
            categoryIconId = it.key,
            icon = it.value,
        )
    }
}
// endregion

// region Extension
fun CategoriesUiState.showButton(): Boolean {
    return categories.any { category ->
        category.categoryName.isNotBlank() &&
                categoryIcons.any { icon -> icon.isSelected } &&
                !isLoading &&
                category.categoryNameState == ValidationState.VALID_TEXT_FIELD
    }
}

fun CategoriesUiState.showLazyCondition() = !this.isLoading && !this.isError
// endregion