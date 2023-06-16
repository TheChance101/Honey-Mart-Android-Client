package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): ValidationState {
        if (password.isBlank()) {
            return ValidationState.BLANK_PASSWORD
        }
        if (password.length < 6) {
            return ValidationState.INVALID_PASSWORD_LENGTH
        }
        if (!isPassword(password)) {
            return ValidationState.INVALID_PASSWORD
        }
        return ValidationState.VALID_PASSWORD

    }

    private fun isPassword(password: String): Boolean {
        return (Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$"))
            .matcher(password).matches()
    }
}