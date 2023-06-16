package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import java.util.regex.Pattern
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

    private fun isValidFullName(userName: String): Boolean =
        (Pattern.compile("^[A-Za-z][A-Za-z0-9_]{4,14}\$"))
            .matcher(userName).matches()

}