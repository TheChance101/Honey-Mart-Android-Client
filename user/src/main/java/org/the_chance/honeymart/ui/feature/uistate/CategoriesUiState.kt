package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.util.ErrorHandler


data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val message: String = "",
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
        categoryImageId = categoryImageId
    )
}