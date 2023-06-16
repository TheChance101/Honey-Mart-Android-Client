package org.the_chance.honeymart.domain.usecase

import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class ValidateConfirmPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String, repeatedPassword: String): Boolean {
        if (password != repeatedPassword) {
            return false
        }
        return true

    }
}