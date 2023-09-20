package org.the_chance.honeymart.ui.features.category

import android.annotation.SuppressLint
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.model.ProductRating
import org.the_chance.honeymart.domain.model.ProductReview
import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.util.toPriceFormat
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Aziza Helmy on 8/7/2023.
 */

// region Ui State
data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isLoadingPaging: Boolean = false,
    val isError: Boolean = false,
    val isErrorPaging: Boolean = false,
    val isEmptyProducts: Boolean = false,
    val error: ErrorHandler? = null,
    val message: String = "",
    val productsQuantity: String = "",
    val position: Int = 0,
    val page: Int = 1,
    val productsScrollPosition: Int = 0,
    val snackBar: SnackBarState = SnackBarState(),
    val category: CategoryUiState = CategoryUiState(),
    val products: List<ProductUiState> = emptyList(),
    val productDetails: ProductUiState = ProductUiState(),
    val categories: List<CategoryUiState> = emptyList(),
    val categoryIcons: List<CategoryIconUIState> = emptyList(),
    val showScreenState: ShowScreenState = ShowScreenState(),
    val newCategory: NewCategoryUiState = NewCategoryUiState(),
    val newProducts: NewProductsUiState = NewProductsUiState(),
)

data class NewProductsUiState(
    val id: Long = 0L,
    val categoryId: Long = 0L,
    val images: List<ByteArray> = emptyList(),
    val productNameState: FieldState = FieldState(),
    val productPriceState: FieldState = FieldState(),
    val productDescriptionState: FieldState = FieldState()
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

data class FieldState(
    val name: String = "",
    val errorState: String = "",
    val isValid: Boolean = errorState.isEmpty()
)


data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryIconUIState: CategoryIconUIState = CategoryIconUIState(),
    val isCategorySelected: Boolean = false,
)

data class NewCategoryUiState(
    val categoryId: Long = 0L,
    val categoryState: FieldState = FieldState(),
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
    val productNameState: FieldState = FieldState(),
    val productImage: List<String> = emptyList(),
    val productPriceState: FieldState = FieldState(),
    val productDescriptionState: FieldState = FieldState(),
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

data class ProductReviewsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val page: Int = 1,
    val reviews: ReviewDetailsUiState = ReviewDetailsUiState(),
)

data class ReviewDetailsUiState(
    val reviewStatisticUiState: ProductRatingUiState = ProductRatingUiState(),
    val reviews: List<ProductReviewUiState> = listOf()
)

fun Reviews.toReviews(): ReviewDetailsUiState {
    return ReviewDetailsUiState(
        reviewStatisticUiState = reviewStatistic.toReviewStatisticUiState(),
        reviews = reviews.map { it.toProductReviewUiState() }
    )
}

data class ProductRatingUiState(
    val averageRating: Double = 0.0,
    val reviewCount: Int = 0,
    val oneStarCount: Int = 0,
    val twoStarsCount: Int = 0,
    val threeStarsCount: Int = 0,
    val fourStarsCount: Int = 0,
    val fiveStarsCount: Int = 0,
)

fun ProductRating.toReviewStatisticUiState(): ProductRatingUiState {
    return ProductRatingUiState(
        averageRating = averageRating,
        reviewCount = reviewsCount,
        oneStarCount = oneStarCount,
        twoStarsCount = twoStarsCount,
        threeStarsCount = threeStarsCount,
        fourStarsCount = fourStarsCount,
        fiveStarsCount = fiveStarsCount
    )
}

data class ProductReviewUiState(
    val reviewId: Long,
    val content: String,
    val rating: Int,
    val reviewDate: String,
    val fullName: String,
)

fun ProductReview.toProductReviewUiState(): ProductReviewUiState {
    return ProductReviewUiState(
        reviewId = reviewId,
        content = content,
        rating = rating,
        reviewDate = reviewDate.toDateFormat(),
        fullName = user.fullName
    )
}

@SuppressLint("SimpleDateFormat")
fun Date.toDateFormat(): String {
    val dateFormat = SimpleDateFormat("dd MMM  HH:mm")
    return dateFormat.format(this)
}