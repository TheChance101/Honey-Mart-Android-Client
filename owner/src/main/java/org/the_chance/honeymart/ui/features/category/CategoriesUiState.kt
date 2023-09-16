package org.the_chance.honeymart.ui.features.category

import androidx.paging.PagingData
import arrow.optics.optics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.util.toPriceFormat

/**
 * Created by Aziza Helmy on 8/7/2023.
 */

// region Ui State
@optics
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
) {
    companion object
}

@optics
data class NewProductsUiState(
    val id: Long = 0L,
    val categoryId: Long = 0L,
    val images: List<ByteArray> = emptyList(),
    val productNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productPriceState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productDescriptionState: ValidationState = ValidationState.VALID_TEXT_FIELD,
) {
    companion object
}

val productNameState: FieldState = FieldState(),
val productPriceState: FieldState = FieldState(),
val productDescriptionState: FieldState = FieldState()
)

@optics
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
) {
    companion object
}

@optics
data class SnackBarState(
    val isShow: Boolean = false,
    val message: String = "",
) {
    companion object
}

@optics
data class FieldState(
    val name: String = "",
    val errorState: String = "",
    val isValid: Boolean = errorState.isEmpty()
) {
    companion object
}

@optics
data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryIconUIState: CategoryIconUIState = CategoryIconUIState(),
    val isCategorySelected: Boolean = false,
) {
    companion object
}

@optics
data class NewCategoryUiState(
    val categoryId: Long = 0L,
    val categoryState: FieldState = FieldState(),
    val newIconId: Int = 0,
    val newIcon: Int = 0,
) {
    companion object
}

@optics
data class CategoryIconUIState(
    val categoryIconId: Int = 0,
    val icon: Int = 0,
    val isSelected: Boolean = false,
) {
    companion object
}

@optics
data class ProductUiState(
    val productId: Long = 0L,
    val productNameState: FieldState = FieldState(),
    val productImage: List<String> = emptyList(),
    val productPriceState: FieldState = FieldState(),
    val productDescriptionState: FieldState = FieldState(),
) {
    companion object
}

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
        productNameState = FieldState(name = productName),
        productDescriptionState = FieldState(name = productDescription),
        productPriceState = FieldState(name = productPrice.toPriceFormat()),
        productImage = productImages.ifEmpty { listOf("", "") }
    )
}

fun Product.toProductDetailsUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productNameState = FieldState(name = productName),
        productDescriptionState = FieldState(name = productDescription),
        productPriceState = FieldState(name = productPrice.toPriceFormat()),
        productImage = productImages.ifEmpty { listOf("", "") },
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

fun CategoriesUiState.showAddUpdateCategoryButton(): Boolean {
    return newCategory.categoryState.name.isNotBlank()
            && categoryIcons.any { categoryIcons -> categoryIcons.isSelected }
            && !isLoading
            && newCategory.categoryState.isValid
}

fun NewProductsUiState.showButton(): Boolean {
    return productNameState.isValid && productPriceState.isValid
            && productDescriptionState.isValid && images.isNotEmpty()
}

fun CategoriesUiState.showSaveUpdateButton(): Boolean {
    return productDetails.productNameState.isValid &&
            productDetails.productPriceState.isValid &&
            productDetails.productDescriptionState.isValid &&
            newProducts.productNameState.isValid &&
            newProducts.productPriceState.isValid &&
            newProducts.productDescriptionState.isValid &&
            newProducts.images.isNotEmpty()

}

fun String.removeDollarSign(): String {
    return this.replace("$", "").trim()
}

fun CategoriesUiState.emptyCategoryPlaceHolder() =
    placeHolderCondition() && !showScreenState.showCategoryProducts
            && !showScreenState.showAddCategory

fun CategoriesUiState.errorPlaceHolderCondition() = isError && !isLoading

fun CategoriesUiState.placeHolderCondition() =
    categories.isEmpty() && !isError && !isLoading

fun CategoriesUiState.showAddProductContent() =
    !isLoading && !showScreenState.showFab && showScreenState.showAddProduct &&
            !showScreenState.showAddCategory && !showScreenState.showUpdateCategory


fun CategoriesUiState.showProductDetailsContent() =
    !isLoading && !showScreenState.showFab && !showScreenState.showAddProduct &&
            !showScreenState.showAddCategory && !showScreenState.showUpdateCategory
            && showScreenState.showProductDetails

fun CategoriesUiState.showProductUpdateContent() =
    !isLoading && !showScreenState.showFab && !showScreenState.showAddProduct
            && !showScreenState.showAddCategory && !showScreenState.showUpdateCategory
            && !showScreenState.showProductDetails && showScreenState.showProductUpdate


fun CategoriesUiState.showCategoryProductsInProduct() =
    !isLoading && !showScreenState.showUpdateCategory
            && !showScreenState.showAddCategory &&
            !showScreenState.showAddProduct &&
            showScreenState.showFab &&
            categories.isNotEmpty()

fun CategoriesUiState.showCategoryProductsInCategory() =
    !isLoading && !showScreenState.showFab


fun CategoriesUiState.showLoadingWhenCategoriesIsEmpty() = isLoading && categories.isEmpty()

// endregion