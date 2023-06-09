package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.Category


data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: String = "",
    val categories: List<CategoryUiState> = emptyList(),
)

data class CategoryUiState(
    val categoryId: Long? = 0L,
    val categoryName: String? = "",
    val categoryImageId: Int? = 0
)

fun Category.asCategoriesUiState(): CategoryUiState {
    return CategoryUiState(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryImageId = categoryImageId
    )
}