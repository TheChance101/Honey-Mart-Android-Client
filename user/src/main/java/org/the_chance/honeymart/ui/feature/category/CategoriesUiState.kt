package org.the_chance.honeymart.ui.feature.category

import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.util.ErrorHandler


data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val message: String = "",
    val categories: List<CategoryUiState> = emptyList(),
    val marketId: Long =0,
)
val test= CategoriesUiState(
    isLoading = false,
    isError = false,
    error = null,
    message = "dapibus",
    categories = listOf(
        CategoryUiState(
            categoryId = 5995,
            categoryName = "Catherine Carson",
            categoryImageId = 1342,
            isCategorySelected = false
        )
    ),
    marketId = 3783
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
        categoryImageId = categoryImageId
    )
}