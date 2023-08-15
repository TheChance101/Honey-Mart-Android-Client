package org.the_chance.honeymart.ui.features.signup.market_info

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.features.signup.FieldState
import org.the_chance.honeymart.ui.features.signup.ValidationToast


data class MarketInfoUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isMarketCreated: Boolean = false,

    val marketNameState: FieldState = FieldState(),
    val marketAddressState: FieldState = FieldState(),
    val marketDescriptionState: FieldState = FieldState(),

    val isMarketImagesEmpty: Boolean = false,

    val validationToast: ValidationToast = ValidationToast(),

    val MAX_IMAGES: Int = 4,

    val images: List<ByteArray> = emptyList(),
)