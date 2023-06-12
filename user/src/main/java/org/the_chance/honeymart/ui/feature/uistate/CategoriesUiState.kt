package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.CategoryEntity


data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: String = "",
    val categories: List<CategoryUiState> = emptyList(),
)

data class CategoryUiState(
    val categoryId: Long? = 0L,
    val categoryName: String? = "",
    val selectedCategory:Boolean = false,
    val categoryImageId: Int? = 0
)

fun CategoryEntity.asCategoriesUiState(): CategoryUiState {
    return CategoryUiState(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryImageId = categoryImageId
    )
}