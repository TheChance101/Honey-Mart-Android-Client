package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationResult
import java.util.regex.Pattern

class ValidateUserNameUseCase {

    fun execute(userName: String): ValidationResult {
        if (userName.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMsg = "The userName can't be blank"
            )
        }
        if (!isUserName(userName)) {
            return ValidationResult(
                isSuccessful = false,
                errorMsg = "That's not a valid userName"
            )
        }
        return ValidationResult(isSuccessful = true)
    }

    fun isUserName(userName: String): Boolean {
        return userName.length > 4 && (Pattern.compile("^[A-Za-z][A-Za-z0-9_]{4,14}\$")) //   ^[A-Za-z]\\w{4,14}$
            .matcher(userName).matches()
    }

}