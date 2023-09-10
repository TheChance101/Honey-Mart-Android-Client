package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class ValidationAdminLoginFieldsUseCase @Inject constructor() {
    fun validateEmail(email: String): ValidationState {
        if (email.isBlank()) {
            return ValidationState.BLANK_EMAIL
        }
        if (!isEmailMatchRegex(email)) {
            return ValidationState.INVALID_EMAIL
        }
        return ValidationState.VALID_EMAIL
    }

    private fun isEmailMatchRegex(email: String): Boolean =
        Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}").matches(email)

    fun validatePassword(password: String): ValidationState {
        return when {
            password.isBlank() -> {
                ValidationState.BLANK_PASSWORD
            }
            password.length < 4 -> {
                ValidationState.INVALID_PASSWORD_LENGTH
            }
            else -> {
                ValidationState.VALID_PASSWORD
            }
        }
    }
}