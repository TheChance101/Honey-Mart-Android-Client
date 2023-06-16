package org.the_chance.honeymart.util

import org.the_chance.honeymart.domain.util.ValidationState

/**
 * Created by Aziza Helmy on 6/16/2023.
 */

fun handleValidation(validationStat: ValidationState): String {

    return when (validationStat) {
        ValidationState.BLANK_EMAIL -> "The email can't be blank"
        ValidationState.INVALID_EMAIL -> "That's not a valid email"
        ValidationState.BLANK_PASSWORD -> "The password can't be blank"
        ValidationState.INVALID_PASSWORD -> "The password needs to contain at least one letter and digit"
        ValidationState.INVALID_CONFIRM_PASSWORD -> "The passwords don't match"
        ValidationState.BLANK_FULL_NAME -> "The userName can't be blank"
        ValidationState.INVALID_FULL_NAME -> "That's not a valid userName"
        ValidationState.INVALID_PASSWORD_LENGTH -> "The password needs to consist of at least 6 characters"
        ValidationState.VALID_EMAIL -> "valid email"
        ValidationState.VALID_PASSWORD -> "valid password"
        ValidationState.VALID_FULL_NAME -> "valid full name"
    }
}