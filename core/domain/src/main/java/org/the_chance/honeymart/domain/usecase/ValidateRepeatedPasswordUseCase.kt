package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationResult
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class ValidateRepeatedPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                isSuccessful = false,
                errorMsg = "The passwords don't match"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}