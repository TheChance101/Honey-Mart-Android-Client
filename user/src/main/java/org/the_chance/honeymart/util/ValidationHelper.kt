package org.the_chance.honeymart.util

import android.content.Context
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.user.R

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class ValidationHelper(val context: Context) {

    fun handleValidation(validationStat: ValidationState): String {

        return when (validationStat) {
            ValidationState.BLANK_EMAIL -> context.getString(R.string.the_email_can_t_be_blank)
            ValidationState.INVALID_EMAIL -> context.getString(R.string.that_s_not_a_valid_email)
            ValidationState.BLANK_PASSWORD -> context.getString(R.string.the_password_can_t_be_blank)
            ValidationState.INVALID_PASSWORD -> context.getString(R.string.the_password_needs_to_contain_at_least_one_letter_and_digit)
            ValidationState.INVALID_CONFIRM_PASSWORD -> context.getString(R.string.the_passwords_don_t_match)
            ValidationState.BLANK_FULL_NAME -> context.getString(R.string.the_username_can_t_be_blank)
            ValidationState.INVALID_FULL_NAME -> context.getString(R.string.that_s_not_a_valid_username)
            ValidationState.INVALID_PASSWORD_LENGTH -> context.getString(R.string.the_password_needs_to_consist_of_at_least_6_characters)
            ValidationState.VALID_EMAIL -> context.getString(R.string.valid_email)
            ValidationState.VALID_PASSWORD -> context.getString(R.string.valid_password)
            ValidationState.VALID_FULL_NAME -> context.getString(R.string.valid_full_name)
        }

    }
}
