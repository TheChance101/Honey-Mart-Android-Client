package org.the_chance.honeymart.util

import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState.*

/**
 * Created by Aziza Helmy on 6/16/2023.
 */

internal fun handleValidation(validationStat: ValidationState): Int {

    return when (validationStat) {
        BLANK_EMAIL -> R.string.the_email_can_t_be_blank
        INVALID_EMAIL -> R.string.that_s_not_a_valid_email
        BLANK_PASSWORD -> R.string.the_password_can_t_be_blank
        INVALID_PASSWORD -> R.string.the_password_needs_to_contain_at_least_one_letter_and_digit
        INVALID_CONFIRM_PASSWORD -> R.string.the_passwords_don_t_match
        BLANK_FULL_NAME -> R.string.the_username_can_t_be_blank
        INVALID_FULL_NAME -> R.string.that_s_not_a_valid_username
        INVALID_PASSWORD_LENGTH_SHORT -> R.string.the_password_needs_to_consist_of_at_least_6_characters
        VALID_EMAIL -> R.string.valid_email
        VALID_PASSWORD -> R.string.valid_password
        VALID_FULL_NAME -> R.string.valid_full_name
        SUCCESS -> R.string.success
        BLANK_TEXT_FIELD -> TODO()
        VALID_TEXT_FIELD -> TODO()
        SHORT_LENGTH_TEXT -> TODO()
        INVALID_PRICE -> TODO()
        PASSWORD_REGEX_ERROR_LETTER -> TODO()
        PASSWORD_REGEX_ERROR_DIGIT -> TODO()
        PASSWORD_REGEX_ERROR_SPECIAL_CHARACTER -> TODO()
        INVALID_MARKET_NAME -> TODO()
        VALID_MARKET_NAME -> TODO()
        BLANK_MARKET_NAME -> TODO()
        BLANK_MARKET_ADDRESS -> TODO()
        INVALID_MARKET_ADDRESS -> TODO()
        VALID_MARKET_ADDRESS -> TODO()
        BLANK_MARKET_DESCRIPTION -> TODO()
        SHORT_MARKET_DESCRIPTION -> TODO()
        VALID_MARKET_DESCRIPTION -> TODO()
        CONFIRM_PASSWORD_DOES_NOT_MATCH -> TODO()
        CONFIRM_PASSWORD_MATCH -> TODO()
        INVALID_COUPON_DISCOUNT_PERCENTAGE -> TODO()
        INVALID_COUPON_COUNT -> TODO()
        INVALID_PASSWORD_LENGTH_LONG -> TODO()
        BLANK_CATEGORY_NAME -> TODO()
        INVALID_CATEGORY_NAME -> TODO()
        VALID_CATEGORY_NAME -> TODO()
        SHORT_CATEGORY_NAME -> TODO()
        LONG_CATEGORY_NAME -> TODO()
        BLANK_PRODUCT_NAME -> TODO()
        INVALID_PRODUCT_NAME -> TODO()
        VALID_PRODUCT_NAME -> TODO()
        SHORT_PRODUCT_NAME -> TODO()
        LONG_PRODUCT_NAME -> TODO()
        BLANK_PRODUCT_PRICE -> TODO()
        INVALID_PRODUCT_PRICE -> TODO()
        VALID_PRODUCT_PRICE -> TODO()
        BLANK_PRODUCT_DESCRIPTION -> TODO()
        SHORT_PRODUCT_DESCRIPTION -> TODO()
        LONG_PRODUCT_DESCRIPTION -> TODO()
        VALID_PRODUCT_DESCRIPTION -> TODO()
        INVALID_TEXT_FIELD -> TODO()
        LONG_LENGTH_TEXT -> TODO()
        BLANK_COUPON_DISCOUNT_PERCENTAGE -> TODO()
        VALID_COUPON_DISCOUNT_PERCENTAGE -> TODO()
        BLANK_COUPON_COUNT -> TODO()
        LONG_COUPON_COUNT -> TODO()
        VALID_COUPON_COUNT -> TODO()
    }

}
