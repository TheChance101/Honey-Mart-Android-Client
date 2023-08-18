package org.the_chance.honeymart.ui.feature.category

import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class MarketDetailsUiState(
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
)
data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryImageId: Int = 0,
    val isCategorySelected: Boolean = false
)

fun CategoryEntity.toCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryImageId = categoryImageId,
    )
}

fun MarketDetailsUiState.showLazyCondition() = !this.isLoading && !this.isError

