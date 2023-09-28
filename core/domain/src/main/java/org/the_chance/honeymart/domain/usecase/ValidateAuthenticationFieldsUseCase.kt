package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class ValidateAuthenticationFieldsUseCase @Inject constructor() {
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

    fun validationFullName(fullName: String): ValidationState {
        if (fullName.isBlank()) {
            return ValidationState.BLANK_FULL_NAME
        }
        if (!isFullNameMatchRegex(fullName)) {
            return ValidationState.INVALID_FULL_NAME

        }
        return ValidationState.VALID_FULL_NAME
    }

    private fun isFullNameMatchRegex(fullName: String): Boolean =
        Regex("^[a-zA-Z]{4,14}\\s[a-zA-Z]{4,14}$").matches(fullName)

    fun validationPassword(password: String): ValidationState {
        return when {
            password.isBlank() -> {
                ValidationState.BLANK_PASSWORD
            }

            password.length < 8 -> {
                ValidationState.INVALID_PASSWORD_LENGTH_SHORT
            }

            password.length > 14 -> {
                ValidationState.INVALID_PASSWORD_LENGTH_LONG
            }

            !password.any { it.isDigit() } -> {
                ValidationState.PASSWORD_REGEX_ERROR_DIGIT
            }

            !password.any { it.isLetter() } -> {
                ValidationState.PASSWORD_REGEX_ERROR_LETTER
            }

            !password.contains(Regex("[!@\$%*&]")) -> {
                ValidationState.PASSWORD_REGEX_ERROR_SPECIAL_CHARACTER
            }

            else -> ValidationState.VALID_PASSWORD
        }

    }

    fun validateConfirmPassword(password: String, repeatedPassword: String): ValidationState {
        return if (password == repeatedPassword) {
            ValidationState.CONFIRM_PASSWORD_MATCH
        } else {
            ValidationState.CONFIRM_PASSWORD_DOES_NOT_MATCH
        }
    }

    fun isFieldBlank(text: String): ValidationState {
        return if (text.isBlank())
            ValidationState.BLANK_TEXT_FIELD
        else
            ValidationState.VALID_TEXT_FIELD
    }
}