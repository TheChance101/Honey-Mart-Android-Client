package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import java.util.regex.Pattern
import javax.inject.Inject

class ValidationSignupFieldsUseCase @Inject constructor() {
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

            password.length < 6 -> {
                ValidationState.INVALID_PASSWORD_LENGTH
            }

            !isPasswordMatchRegex(password) -> {
                ValidationState.INVALID_PASSWORD
            }

            else -> ValidationState.VALID_PASSWORD
        }

    }

    private fun isPasswordMatchRegex(password: String): Boolean {
        return (Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$"))
            .matcher(password).matches()
    }

    fun validateConfirmPassword(password: String, repeatedPassword: String) =
        password == repeatedPassword
}
