package org.the_chance.honeymart.ui.features.category

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.util.toPriceFormat

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
    val category: CategoryUiState = CategoryUiState(),
    val products: Flow<PagingData<ProductUiState>> = flow {},
    val productDetails: ProductUiState = ProductUiState(),
    val categories: List<CategoryUiState> = emptyList(),
    val categoryIcons: List<CategoryIconUIState> = emptyList(),
    val showScreenState: ShowScreenState = ShowScreenState(),
    val newCategory: NewCategoryUiState = NewCategoryUiState(),
    val newProducts: NewProductsUiState = NewProductsUiState()
)

data class NewProductsUiState(
    val id: Long = 0L,
    val categoryId: Long = 0L,
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
    val showProductUpdate: Boolean = false,
    val showCategoryProducts: Boolean = false,
    val showDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val showFab: Boolean = true,
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
    val productImage: List<String> = emptyList(),
    val productPrice: String = "",
    val productDescription: String = "",
    val productsQuantity: String = "",
)

enum class Visibility {
    UPDATE_CATEGORY, ADD_CATEGORY, DELETE_CATEGORY, DELETE_PRODUCT,
}
// endregion

// region Mapper
fun List<Category>.toCategoryUiState(): List<CategoryUiState> {
    return map {
        CategoryUiState(
            categoryId = it.categoryId,
            categoryName = it.categoryName,
            categoryIconUIState = CategoryIconUIState(categoryIconId = it.categoryImageId)
        )
    }
}

fun Product.toProductUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productName = productName,
        productDescription = productDescription,
        productPrice = productPrice.toPriceFormat(),
        productImage = productImages.ifEmpty { listOf("", "") }
    )
}

fun Product.toProductDetailsUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productName = productName,
        productImage = productImages.ifEmpty { listOf("", "") },
        productPrice = productPrice.toPriceFormat(),
        productDescription = productDescription,
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
        newCategory.newCategoryName.isNotBlank() && !category.categoryIconUIState.isSelected && !isLoading && newCategory.categoryNameState == ValidationState.VALID_TEXT_FIELD
    }
}

fun NewProductsUiState.showButton(): Boolean {
    return name.isNotBlank() && price.isNotBlank() && description.isNotBlank() && productNameState == ValidationState.VALID_TEXT_FIELD && productPriceState == ValidationState.VALID_TEXT_FIELD && productDescriptionState == ValidationState.VALID_TEXT_FIELD && images.isNotEmpty()
}

fun ProductUiState.showButton(): Boolean {
    return productName.isNotBlank() && productPrice.isNotBlank() && productDescription.isNotBlank() && productImage.isNotEmpty()
}


fun CategoriesUiState.emptyCategoryPlaceHolder() =
    placeHolderCondition() && !showScreenState.showCategoryProducts

fun CategoriesUiState.errorPlaceHolderCondition() = isError

fun CategoriesUiState.placeHolderCondition() = categories.isEmpty() && !isError && !isLoading

fun CategoriesUiState.showAddProductContent() =
    !isLoading && !showScreenState.showFab && showScreenState.showAddProduct && !showScreenState.showAddCategory && !showScreenState.showUpdateCategory


fun CategoriesUiState.showProductDetailsContent() =
    !isLoading && !showScreenState.showFab && !showScreenState.showAddProduct && !showScreenState.showAddCategory && !showScreenState.showUpdateCategory && showScreenState.showProductDetails

fun CategoriesUiState.showProductUpdateContent() =
    !isLoading && !showScreenState.showFab && !showScreenState.showAddProduct && !showScreenState.showAddCategory && !showScreenState.showUpdateCategory && !showScreenState.showProductDetails && showScreenState.showProductUpdate


fun CategoriesUiState.showCategoryProductsInProduct() =
    !isLoading && !showScreenState.showUpdateCategory && !showScreenState.showAddCategory && !showScreenState.showAddProduct && showScreenState.showFab

fun CategoriesUiState.showCategoryProductsInCategory() = !isLoading && !showScreenState.showFab

fun CategoriesUiState.showLoadingWhenCategoriesIsEmpty() = isLoading && categories.isEmpty()

// endregion