package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationResult
import java.util.regex.Pattern

class ValidatePasswordUseCase {

    fun execute(password: String): ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                isSuccessful = false,
                errorMsg = "The password needs to consist of at least 8 characters"
            )
        }
        if(!isPassword(password)) {
            return ValidationResult(
                isSuccessful = false,
                errorMsg = "The password needs to contain at least one letter and digit"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }

    private fun isPassword(password: String): Boolean {
        return (Pattern.compile("^(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[@_^%\$#?*&!-]).{8,16}"))
            .matcher(password).matches()
    }
}