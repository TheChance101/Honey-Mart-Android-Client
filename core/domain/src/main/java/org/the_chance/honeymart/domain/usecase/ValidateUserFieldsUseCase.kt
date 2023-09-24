package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class ValidateUserFieldsUseCase @Inject constructor() {
    fun validateReview(productDescription: String): ValidationState {
        return when {
            productDescription.isBlank() -> ValidationState.BLANK_REVIEW
            productDescription.length < 6 -> ValidationState.SHORT_REVIEW
            productDescription.length > 500 -> ValidationState.LONG_REVIEW
            else -> ValidationState.VALID_REVIEW
        }
    }
}

