package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): ValidationState {
        return when {
            password.isBlank() -> {
                ValidationState.BLANK_PASSWORD
            }

            password.length < 6 -> {
                ValidationState.INVALID_PASSWORD_LENGTH
            }

            !isPassword(password) -> {
                ValidationState.INVALID_PASSWORD
            }

            else -> ValidationState.VALID_PASSWORD
        }

    }

    private fun isPassword(password: String): Boolean {
        return (Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$"))
            .matcher(password).matches()
    }
}