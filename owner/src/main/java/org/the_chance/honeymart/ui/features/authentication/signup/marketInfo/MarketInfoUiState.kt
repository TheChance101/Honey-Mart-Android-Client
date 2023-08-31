package org.the_chance.honeymart.ui.features.authentication.signup.marketInfo

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.features.authentication.signup.FieldState
import org.the_chance.honeymart.ui.features.authentication.signup.ValidationToast

data class MarketInfoUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isMarketCreated: Boolean = false,

    val marketNameState: FieldState = FieldState(),
    val marketAddressState: FieldState = FieldState(),
    val marketDescriptionState: FieldState = FieldState(),

    val isMarketImageEmpty: Boolean = true,

    val validationToast: ValidationToast = ValidationToast(),

    val MAX_IMAGES: Int = 4,

    val image: ByteArray = byteArrayOf(),
)