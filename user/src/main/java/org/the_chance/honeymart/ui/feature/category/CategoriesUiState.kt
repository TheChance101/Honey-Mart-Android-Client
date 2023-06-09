package org.the_chance.honeymart.ui.feature.category

import org.the_chance.honeymart.domain.model.Category


data class CategoriesUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: String = "",
    val categories: List<CategoryUiState> = emptyList(),
)

data class CategoryUiState(
    val id: Long = 0L,
    val name: String = "",
    val imageId: Int = 0
)

fun Category.asCategoriesUiState(): CategoryUiState {
    return CategoryUiState(
        id = id,
        name = name,
        imageId = imageId
    )
}