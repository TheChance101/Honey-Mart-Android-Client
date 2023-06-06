package org.the_chance.ui.category.uistate

import org.the_chance.honeymart.ui.feature.category.uistate.CategoriesUiState


data class CategoryUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: String = "",
    val marketCategories: List<CategoriesUiState> = emptyList(),
)


