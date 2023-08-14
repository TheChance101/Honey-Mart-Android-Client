package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class ValidateCreateMarketFieldsUseCase @Inject constructor() {
    fun validateMarketName(marketName: String): ValidationState {
        if (marketName.isBlank()) {
            return ValidationState.BLANK_MARKET_NAME
        }
        if (!isMarketNameMatchRegex(marketName)) {
            return ValidationState.INVALID_MARKET_NAME
        }
        return ValidationState.VALID_MARKET_NAME
    }

    private fun isMarketNameMatchRegex(marketName: String): Boolean =
        Regex("^[A-Za-z0-9\\s\\[\\]()\\-.,&]{4,20}$").matches(marketName)

    fun validateMarketAddress(marketAddress: String): ValidationState {
        if (marketAddress.isBlank()) {
            return ValidationState.BLANK_MARKET_ADDRESS
        }
        return ValidationState.VALID_MARKET_ADDRESS
    }

    fun validateMarketDescription(marketDescription: String): ValidationState {
        if (marketDescription.isBlank()) {
            return ValidationState.BLANK_MARKET_DESCRIPTION
        }
        if (marketDescription.length < 20) {
            return ValidationState.SHORT_MARKET_DESCRIPTION
        }
        return ValidationState.VALID_MARKET_DESCRIPTION
    }

    fun validateMarketImages(marketImages: List<ByteArray>): ValidationState {
        if (marketImages.isEmpty()) {
            return ValidationState.EMPTY_MARKET_IMAGES
        }
        return ValidationState.VALID_MARKET_IMAGES
    }
}