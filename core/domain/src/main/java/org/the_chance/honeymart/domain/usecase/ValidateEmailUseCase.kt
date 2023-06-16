package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationResult
import java.util.regex.Pattern

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class ValidateEmailUseCase {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMsg = "The email can't be blank"
            )
        }
        if (!isEmail(email)) {
            return ValidationResult(
                isSuccessful = false,
                errorMsg = "That's not a valid email"
            )
        }
        return ValidationResult(isSuccessful = true)
    }

     private fun isEmail(email: String): Boolean {
        return Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            .matcher(email).matches()
    }
}