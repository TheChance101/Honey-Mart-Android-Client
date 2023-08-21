package org.the_chance.honeymart.ui.features.category

import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

/**
 * Created by Aziza Helmy on 8/7/2023.
 */

// region Ui State
data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isEmptyProducts: Boolean = false,
    val error: ErrorHandler? = null,
    val message: String = "",
    val productsQuantity: String = "",
    val position: Int = 0,
    val snackBar: SnackBarState = SnackBarState(),
    val category: CategoryUiState = CategoryUiState(0, ""),
    val products: List<ProductUiState> = emptyList(),
    val categories: List<CategoryUiState> = emptyList(),
    val categoryIcons: List<CategoryIconUIState> = emptyList(),
    val showScreenState: ShowScreenState = ShowScreenState(),
    val newCategory: NewCategoryUiState = NewCategoryUiState(),
    val newProducts: NewProductsUiState= NewProductsUiState()
)

data class NewProductsUiState(
    val id: Long = 0L,
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val images: List<ByteArray> = emptyList(),
    val productNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productPriceState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productDescriptionState: ValidationState = ValidationState.VALID_TEXT_FIELD,
)

data class ShowScreenState(
    val showAddCategory: Boolean = false,
    val showUpdateCategory: Boolean = false,
    val showAddProduct: Boolean = false,
    val showProductDetails: Boolean = false,
    val showCategoryProducts: Boolean = false,
    val showDialog: Boolean = false,
    val showFab : Boolean = true ,

    )

data class SnackBarState(
    val isShow: Boolean = false,
    val message: String = "",
)

data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryIconUIState: CategoryIconUIState = CategoryIconUIState(),
    val isCategorySelected: Boolean = false,
)

data class NewCategoryUiState(
    val categoryId: Long = 0L,
    val newCategoryName: String = "",
    val categoryNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val newIconId: Int = 0,
    val newIcon: Int = 0,
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
fun List<CategoryEntity>.toCategoryUiState(): List<CategoryUiState> {
    return map {
        CategoryUiState(
            categoryId = it.categoryId,
            categoryName = it.categoryName,
            categoryIconUIState = CategoryIconUIState(categoryIconId = it.categoryImageId)
        )
    }
}

fun List<ProductEntity>.toProductUiState(): List<ProductUiState> {
    return map {
        ProductUiState(
            productId = it.productId,
            productName = it.productName,
            productImage = it.productImages.first(),
            productPrice = "${it.ProductPrice}$",
        )
    }
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
        newCategory.newCategoryName.isNotBlank() &&
                !category.categoryIconUIState.isSelected &&
                !isLoading &&
                newCategory.categoryNameState == ValidationState.VALID_TEXT_FIELD
    }
}

fun NewProductsUiState.showButton(): Boolean {
    return name.isNotBlank()
            && price.isNotBlank()
            && description.isNotBlank()
            && productNameState == ValidationState.VALID_TEXT_FIELD
            && productPriceState == ValidationState.VALID_TEXT_FIELD
            && productDescriptionState == ValidationState.VALID_TEXT_FIELD
}


fun CategoriesUiState.showLazyCondition() = !this.isLoading && !this.isError
// endregion