package org.the_chance.ui.category.uistate


data class CategoryUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: String = "",
    val markets: List<CategoriesUiState> = emptyList(),
)

