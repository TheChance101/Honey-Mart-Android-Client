package org.the_chance.honeymart.util

import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.design_system.R

/**
 * Created by Aziza Helmy on 6/16/2023.
 */


internal fun handleValidation(validationStat: ValidationState): Int {

    return when (validationStat) {
        ValidationState.BLANK_EMAIL -> R.string.the_email_can_t_be_blank
        ValidationState.INVALID_EMAIL -> R.string.that_s_not_a_valid_email
        ValidationState.BLANK_PASSWORD -> R.string.the_password_can_t_be_blank
        ValidationState.INVALID_PASSWORD -> R.string.the_password_needs_to_contain_at_least_one_letter_and_digit
        ValidationState.INVALID_CONFIRM_PASSWORD -> R.string.the_passwords_don_t_match
        ValidationState.BLANK_FULL_NAME -> R.string.the_username_can_t_be_blank
        ValidationState.INVALID_FULL_NAME -> R.string.that_s_not_a_valid_username
        ValidationState.INVALID_PASSWORD_LENGTH -> R.string.the_password_needs_to_consist_of_at_least_6_characters
        ValidationState.VALID_EMAIL -> R.string.valid_email
        ValidationState.VALID_PASSWORD -> R.string.valid_password
        ValidationState.VALID_FULL_NAME -> R.string.valid_full_name
        ValidationState.SUCCESS -> R.string.success
        ValidationState.BLANK_TEXT_FIELD -> TODO()
        ValidationState.VALID_TEXT_FIELD -> TODO()
        ValidationState.SHORT_LENGTH_TEXT -> TODO()
        ValidationState.INVALID_PRICE -> TODO()
        ValidationState.PASSWORD_REGEX_ERROR_LETTER -> TODO()
        ValidationState.PASSWORD_REGEX_ERROR_DIGIT -> TODO()
        ValidationState.PASSWORD_REGEX_ERROR_SPECIAL_CHARACTER -> TODO()
        ValidationState.INVALID_MARKET_NAME -> TODO()
        ValidationState.VALID_MARKET_NAME -> TODO()
        ValidationState.BLANK_MARKET_NAME -> TODO()
        ValidationState.BLANK_MARKET_ADDRESS -> TODO()
        ValidationState.INVALID_MARKET_ADDRESS -> TODO()
        ValidationState.VALID_MARKET_ADDRESS -> TODO()
        ValidationState.BLANK_MARKET_DESCRIPTION -> TODO()
        ValidationState.SHORT_MARKET_DESCRIPTION -> TODO()
        ValidationState.VALID_MARKET_DESCRIPTION -> TODO()
        ValidationState.CONFIRM_PASSWORD_DOES_NOT_MATCH -> TODO()
        ValidationState.CONFIRM_PASSWORD_MATCH -> TODO()
        ValidationState.INVALID_COUPON_DISCOUNT_PERCENTAGE -> TODO()
        ValidationState.INVALID_COUPON_COUNT -> TODO()
    }

}
