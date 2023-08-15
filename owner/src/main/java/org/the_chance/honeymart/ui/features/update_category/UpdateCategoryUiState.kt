package org.the_chance.honeymart.ui.features.update_category

import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.features.add_product.AddProductUiState

data class UpdateCategoryUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryIconId: Int = 0,
    val categoryNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val categoryIcons: List<CategoryIconUIState> = emptyList()
)

data class CategoryIconUIState(
    val categoryIconId: Int = 0,
    val icon: Int = 0,
    val isCategorySelected: Boolean = false,
)

fun UpdateCategoryUiState.showButton(): Boolean {
    return categoryName.isNotBlank()
            && categoryIcons.any { it.isCategorySelected }
            && !isLoading
            && categoryNameState == ValidationState.VALID_TEXT_FIELD
}

fun Map<Int, Int>.toCategoryImageUIState() =
    map {
        CategoryIconUIState(
            categoryIconId = it.key,
            icon = it.value,
        )
    }

val categoryIcons = mapOf(
    1 to R.drawable.ic_chef_hat,
    2 to R.drawable.ic_calculator,
    3 to R.drawable.ic_airbuds,
    4 to R.drawable.ic_book,
    5 to R.drawable.ic_glasses,
    6 to R.drawable.ic_cat,
    7 to R.drawable.ic_devices,
    8 to R.drawable.ic_earth,
    9 to R.drawable.ic_ufo,
    10 to R.drawable.ic_chandelier,
    11 to R.drawable.ic_umbrella,
    12 to R.drawable.ic_adhesive_plaster,
    13 to R.drawable.ic_cup,
    14 to R.drawable.ic_case_round_minimalistic,
    15 to R.drawable.ic_confetti_minimalistic,
    16 to R.drawable.ic_scissors,
    17 to R.drawable.ic_t_shirt,
    18 to R.drawable.ic_telescope,
    19 to R.drawable.ic_laptop,
    20 to R.drawable.ic_sofa,
)
