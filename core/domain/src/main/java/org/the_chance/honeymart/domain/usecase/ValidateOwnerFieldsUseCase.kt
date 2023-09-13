package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class ValidateOwnerFieldsUseCase @Inject constructor() {
    fun validateCategoryNameField(categoryName: String): ValidationState {
        return when {
            categoryName.isBlank() -> ValidationState.BLANK_CATEGORY_NAME
            categoryName.length < 4 -> ValidationState.SHORT_CATEGORY_NAME
            categoryName.length > 16 -> ValidationState.LONG_CATEGORY_NAME
            !categoryName.matches(Regex("^[a-zA-Z]{4,16}$")) -> {
                ValidationState.INVALID_CATEGORY_NAME
            }

            else -> ValidationState.VALID_CATEGORY_NAME
        }
    }
}

