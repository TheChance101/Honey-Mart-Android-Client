package org.the_chance.honeymart.domain.util

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
enum class ValidationState {
    BLANK_EMAIL,
    INVALID_EMAIL,
    BLANK_PASSWORD,
    INVALID_PASSWORD,
    INVALID_PASSWORD_LENGTH,
    INVALID_CONFIRM_PASSWORD,
    BLANK_FULL_NAME,
    INVALID_FULL_NAME,
    VALID_EMAIL,
    VALID_PASSWORD,
    VALID_FULL_NAME,
    SUCCESS
}