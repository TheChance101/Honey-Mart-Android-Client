package org.the_chance.honeymart.ui.features.signup

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.features.signup.market_info.MarketInfoUiState

data class SignupUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isSignUp: Boolean = false,

    val marketInfoUiState: MarketInfoUiState = MarketInfoUiState(),

    val fullNameState: FieldState = FieldState(),
    val emailState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState(),
    val confirmPasswordState: FieldState = FieldState(),

    val validationToast: ValidationToast = ValidationToast()
)

data class FieldState(
    val value: String = "",
    val errorState: String = "",
    val isValid: Boolean = errorState.isNotEmpty()
)

data class ValidationToast(
    val isShow: Boolean = false,
    val message: String = "Please fill all required fields"
)

