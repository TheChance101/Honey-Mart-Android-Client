package org.the_chance.honeymart.ui.features.login

import org.the_chance.honeymart.domain.util.ErrorHandler

data class LoginUiState(
    val isLoading: Boolean = false,
    val authLoading:Boolean = false,
    val error: ErrorHandler? = null,

    val emailState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState(),
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
