package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class ValidateFullNameUseCase @Inject constructor() {

    operator fun invoke(fullName: String): ValidationState {
        if (fullName.isBlank()) {
            return ValidationState.BLANK_FULL_NAME
        }
        if (!isValidFullName(fullName)) {
            return ValidationState.INVALID_FULL_NAME

        }
        return ValidationState.VALID_FULL_NAME
    }

    private fun isValidFullName(fullName: String): Boolean =
        Regex("^[a-zA-Z]{4,14}\\s[a-zA-Z]{4,14}$").matches(fullName)

}