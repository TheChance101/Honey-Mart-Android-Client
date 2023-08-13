package org.the_chance.honeymart.ui.features.update_category

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

data class UpdateCategoryUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val categoryId: Long = 0L,
    val categoryIcon: Int = 0,
    val categoryName: String = "",
    val categoryNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val categoryImages: List<CategoryIconUIState> = emptyList()
)

data class CategoryIconUIState(
    val categoryIconId: Int = 0,
    val icon: Int = 0,
    val isCategorySelected: Boolean = false,
)
