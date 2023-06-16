package org.the_chance.honeymart.domain.usecase

import java.util.regex.Pattern
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {

    operator fun invoke(userName: String): Boolean {
        if (userName.isBlank()) {
            return false
        }
        if (!isUserName(userName)) {
            return false

        }
        return true
    }

    private fun isUserName(userName: String): Boolean =
        (Pattern.compile("^[A-Za-z][A-Za-z0-9_]{4,14}\$"))
            .matcher(userName).matches()

}