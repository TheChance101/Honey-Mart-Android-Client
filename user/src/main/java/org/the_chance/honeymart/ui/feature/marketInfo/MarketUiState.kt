package org.the_chance.honeymart.ui.feature.marketInfo

import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.util.ErrorHandler

data class MarketInfoUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val message: String = "",
    val marketName: String = "",
    val imageUrl: String = "",
    val productsCount: Int = 0,
    val categoriesCount: Int = 0,
    val address: String = "",
    val categories: List<CategoryUiState> = emptyList(),
) {
    val productsCountState = "$productsCount Items"
    val categoriesCountState = "$categoriesCount Categories"
}
data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryImageId: Int = 0,
    val isCategorySelected: Boolean = false
)

fun Category.toCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryImageId = categoryImageId,
    )
}

fun MarketInfoUiState.showLazyCondition() = !this.isLoading && !this.isError

