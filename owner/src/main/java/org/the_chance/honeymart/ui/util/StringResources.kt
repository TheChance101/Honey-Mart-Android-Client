package org.the_chance.honeymart.ui.util

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

            ValidationState.BLANK_CATEGORY_NAME to getStringFromFile(R.string.blank_field),
            ValidationState.SHORT_CATEGORY_NAME to getStringFromFile(R.string.category_name_is_too_short),
            ValidationState.LONG_CATEGORY_NAME to getStringFromFile(R.string.category_name_is_too_long),
            ValidationState.INVALID_CATEGORY_NAME to getStringFromFile(R.string.invalid_category_name),

            ValidationState.BLANK_PRODUCT_NAME to getStringFromFile(R.string.product_name_can_t_be_blank),
            ValidationState.SHORT_PRODUCT_NAME to getStringFromFile(R.string.product_name_is_too_short),
            ValidationState.LONG_PRODUCT_NAME to getStringFromFile(R.string.product_name_is_too_long),
            ValidationState.INVALID_PRODUCT_NAME to getStringFromFile(R.string.invalid_product_name),

            ValidationState.BLANK_PRODUCT_PRICE to getStringFromFile(R.string.product_price_can_t_be_blank),
            ValidationState.INVALID_PRODUCT_PRICE to getStringFromFile(R.string.invalid_product_price),

            ValidationState.BLANK_PRODUCT_DESCRIPTION to getStringFromFile(R.string.product_description_can_t_be_blank),
            ValidationState.SHORT_PRODUCT_DESCRIPTION to getStringFromFile(R.string.product_description_is_too_short),
            ValidationState.LONG_PRODUCT_DESCRIPTION to getStringFromFile(R.string.product_description_is_too_long),

            ValidationState.INVALID_COUPON_DISCOUNT_PERCENTAGE to getStringFromFile(R.string.invalid_coupon_discount_percentage),
            ValidationState.BLANK_COUPON_DISCOUNT_PERCENTAGE to getStringFromFile(R.string.discount_percentage_can_t_be_blank),

            ValidationState.INVALID_COUPON_COUNT to getStringFromFile(R.string.invalid_coupon_count),
            ValidationState.LONG_COUPON_COUNT to getStringFromFile(R.string.coupon_count_is_too_long),
            ValidationState.BLANK_COUPON_COUNT to getStringFromFile(R.string.coupon_count_can_t_be_blank)


        )
    override val errorString: Map<ErrorHandler, String>
        get() = mapOf(
            ErrorHandler.MarketAlreadyExist to getStringFromFile(R.string.market_already_exist),
            ErrorHandler.UsernameAlreadyExist to getStringFromFile(R.string.user_name_already_exist),
            ErrorHandler.EmailAlreadyExist to getStringFromFile(R.string.account_already_exist),
            ErrorHandler.UnKnownUser to getStringFromFile(R.string.something_went_wrong_please_try_again),
            ErrorHandler.UnAuthorized to getStringFromFile(R.string.Invalid_username_or_password),
            ErrorHandler.NoConnection to getStringFromFile(R.string.something_went_wrong_please_try_again),
            ErrorHandler.MarketDeleted to getStringFromFile(R.string.market_has_been_deleted)
        )
    override val requiredFieldsMessageString: String
        get() = getStringFromFile(R.string.please_fill_required_fields)
}