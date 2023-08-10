package org.the_chance.honeymart.ui.features.signup

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

data class SignupUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isSignUp: Boolean = false,
    val isLogin: ValidationState = ValidationState.INVALID_CONFIRM_PASSWORD,

    val fullNameState: FieldState = FieldState(),
    val emailState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState(),
    val confirmPasswordState: FieldState = FieldState(),

    val showToast: Boolean = false
)

data class FieldState(
    val value: String = "",
    val errorState: String = "",
    val isValid: Boolean = false
)

