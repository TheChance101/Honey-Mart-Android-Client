package org.the_chance.honeymart.ui.features.login

import org.the_chance.honeymart.domain.util.ErrorHandler

data class LoginUiState(
    val isLoading: Boolean = false,
    val isAuthenticating:Boolean = false,
    val error: ErrorHandler? = null,
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val validationToast: ValidationToast = ValidationToast()
)

data class FieldState(
    val value: String = "",
    val error: String = "",
    val isEmpty: Boolean = error.isNotEmpty(),
)

data class ValidationToast(
    val isShown: Boolean = false,
    val messageEmptyFields: String = "Please fill all required fields",
    val messageInvalidDetails: String = "Invalid sign in ID or password"
)
