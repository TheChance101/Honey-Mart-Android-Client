package org.the_chance.honeymart.domain.usecase

import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): Boolean {
        if (password.isBlank()) {
            return false
        }
        if (password.length < 6) {
            return false
        }
        if (!isPassword(password)) {
            return false
        }
        return true

    }

    private fun isPassword(password: String): Boolean {
        return (Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$"))
            .matcher(password).matches()
    }
}