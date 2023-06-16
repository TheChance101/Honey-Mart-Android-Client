package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationResult

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class ValidateRepeatedPasswordUseCase{
     fun execute(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword) {
            return ValidationResult(
                isSuccessful  = false,
                errorMsg  = "The passwords don't match"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}