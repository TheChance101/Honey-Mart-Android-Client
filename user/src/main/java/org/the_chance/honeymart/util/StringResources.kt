package org.the_chance.honeymart.util

import android.content.Context
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.design_system.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringResources @Inject constructor(
    val context: Context
) : StringDictionary {
    private fun getStringFromFile(fileId: Int): String {
        return context.resources.getString(fileId)
    }

    override val validationString: Map<ValidationState, String>
        get() = mapOf(
            ValidationState.BLANK_EMAIL to getStringFromFile(R.string.email_should_not_be_empty),
            ValidationState.INVALID_EMAIL to getStringFromFile(R.string.invalid_email),
            ValidationState.BLANK_PASSWORD to getStringFromFile(R.string.password_should_not_be_empty),
            ValidationState.INVALID_PASSWORD_LENGTH_SHORT to getStringFromFile(R.string.password_length_must_be_at_least_8),
            ValidationState.INVALID_PASSWORD_LENGTH_LONG to getStringFromFile(R.string.password_length_must_be_at_least_14),
            ValidationState.PASSWORD_REGEX_ERROR_SPECIAL_CHARACTER to getStringFromFile(R.string.please_write_at_least_1_special_character),
            ValidationState.PASSWORD_REGEX_ERROR_DIGIT to getStringFromFile(R.string.please_write_at_least_1_digit),
            ValidationState.PASSWORD_REGEX_ERROR_LETTER to getStringFromFile(R.string.please_write_at_least_1_letter),
            ValidationState.CONFIRM_PASSWORD_DOES_NOT_MATCH to getStringFromFile(R.string.password_does_not_match),
            ValidationState.BLANK_FULL_NAME to getStringFromFile(R.string.name_should_not_be_empty),
            ValidationState.INVALID_FULL_NAME to getStringFromFile(R.string.invalid_name),
            ValidationState.BLANK_MARKET_NAME to getStringFromFile(R.string.market_name_should_not_be_empty),
            ValidationState.INVALID_MARKET_NAME to getStringFromFile(R.string.invalid_market_name),
            ValidationState.BLANK_MARKET_ADDRESS to getStringFromFile(R.string.market_address_should_not_be_empty),
            ValidationState.INVALID_MARKET_ADDRESS to getStringFromFile(R.string.invalid_market_address),
            ValidationState.SHORT_MARKET_DESCRIPTION to getStringFromFile(R.string.market_description_should_be_20_letter_at_least),
            ValidationState.BLANK_MARKET_DESCRIPTION to getStringFromFile(R.string.market_description_should_not_be_empty),
            ValidationState.BLANK_REVIEW to getStringFromFile(R.string.review_cannot_be_blank),
            ValidationState.SHORT_REVIEW to getStringFromFile(R.string.review_cannot_be_less_than_6_characters),
            ValidationState.LONG_REVIEW to getStringFromFile(R.string.review_cannot_be_more_than_500_characters),
        )

    override val errorString: Map<ErrorHandler, String>
        get() = mapOf(
            ErrorHandler.EmailAlreadyExist to getStringFromFile(R.string.account_already_exist),
            ErrorHandler.UnKnownUser to getStringFromFile(R.string.something_went_wrong_please_try_again),
            ErrorHandler.InvalidUserNameOrPassword to getStringFromFile(R.string.Invalid_username_or_password),
            ErrorHandler.NoConnection to getStringFromFile(R.string.something_went_wrong_please_try_again),
            ErrorHandler.MarketDeleted to getStringFromFile(R.string.market_has_been_deleted)
        )
    override val requiredFieldsMessageString: String
        get() = getStringFromFile(R.string.please_fill_required_fields)

    override val addReviewSuccessString: String
        get() = getStringFromFile(R.string.review_added_successfully)
}